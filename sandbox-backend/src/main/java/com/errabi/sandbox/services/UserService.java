package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Role;
import com.errabi.sandbox.entities.User;
import com.errabi.sandbox.exception.ErrorResponse;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.UserRepository;
import com.errabi.sandbox.utils.TokenInfo;
import com.errabi.sandbox.utils.TokenResponse;
import com.errabi.sandbox.web.mapper.RoleMapper;
import com.errabi.sandbox.web.mapper.UserMapper;
import com.errabi.sandbox.web.model.AuthDto;
import com.errabi.sandbox.web.model.RoleDto;
import com.errabi.sandbox.web.model.UserDto;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.errabi.sandbox.utils.SandboxConstant.*;
import static com.errabi.sandbox.utils.SandboxUtils.buildSuccessInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final RoleMapper roleMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final MailService mailService;

    public TokenResponse userLogin(AuthDto userDto) {
        User user = validateUserCredentials(userDto);
        String token = generateJwtToken(user);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setTokenInfo(TokenInfo.builder()
                .errorCode(SUCCESS_CODE)
                .errorDescription(SUCCESS_CODE_DESCRIPTION)
                .token(token)
                .build());
        return tokenResponse;
    }

    private User validateUserCredentials(AuthDto userDto) {
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                return user;
            } else {
                throw new TechnicalException(
                        INVALID_USERNAME_OR_PASSWORD_CODE,
                        INVALID_USERNAME_OR_PASSWORD_DESCRIPTION,
                        HttpStatus.UNAUTHORIZED
                );
            }
        } else {
            throw new TechnicalException(
                    INVALID_USERNAME_OR_PASSWORD_CODE,
                    INVALID_USERNAME_OR_PASSWORD_DESCRIPTION,
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    private String generateJwtToken(User user) {
        try {
            return jwtTokenService.generateToken(user);
        } catch (JOSEException e) {
            throw new TechnicalException(
                    TOKEN_GENERATION_ERROR_CODE,
                    TOKEN_GENERATION_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public UserDto createUser(UserDto userDto){
        if (userRepository.existsByUsername(userDto.getUsername())) {
            log.error("Failed to save user. Username {} already exists.", userDto.getUsername());
            throw new TechnicalException(
                    USER_ALREADY_EXISTS_ERROR_CODE,
                    USER_ALREADY_EXISTS_ERROR_DESCRIPTION,
                    HttpStatus.CONFLICT);
        }
        try {
            log.info("Creating User {} ..", userDto.getUsername());
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User user = userRepository.save(userMapper.toEntity(userDto));
            Map<String, String> templateParams = Map.of(
                    "firstName", userDto.getFirstName(),
                    "lastName", userDto.getLastName()
            );
            mailService.sendEmail(
                    userDto.getEmail(),
                    "Welcome to our platform!",
                    "welcomeEmail",
                    templateParams
            );
            userDto = userMapper.toDto(user);
            userDto.setPassword(StringUtils.EMPTY);
            userDto.setResponseInfo(buildSuccessInfo());
            return userDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the User", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    SAVE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    private String getStatus(String status){
        return "all".equalsIgnoreCase(status)||"expired".equalsIgnoreCase(status)?null: !"inactive".equalsIgnoreCase(status)?"active":"inactive";
    }
    @Transactional
    public Page<UserDto> findUsersByFilter(String status,String email,String username,Pageable pageable){
        log.info("find users by query ...");
        User userProb =  User.builder()
                .email(StringUtils.isEmpty(email)?null:email)
                .enabled(FILTER_QUERY_ALL.equalsIgnoreCase(status)||FILTER_QUERY_EXPIRED.equalsIgnoreCase(status)?null: !FILTER_QUERY_INACTIVE.equalsIgnoreCase(status))
                .username(StringUtils.isEmpty(username)?null:username)
                .accountNonExpired("expired".equalsIgnoreCase(status)?false:null)
                .build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id","version");
        Example<User> example = Example.of(userProb, matcher);

        return userRepository.findAll(example,pageable)
                      .map(userMapper::toDto);

    }

    public UserDto findUserById(Long userId) {
        log.info("Finding User with id {}",userId);
        Optional<User> optionalUser =  userRepository.findById(userId);
        if(optionalUser.isPresent()){
            UserDto userDto = userMapper.toDto(optionalUser.get());
            userDto.setResponseInfo(buildSuccessInfo());
            return userDto;
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND);
        }
    }
   @Transactional
    public Page<UserDto> findAllUsers(Pageable pageable) {
        try {
            log.info("Fetching all users...");
            Page<User> users = userRepository.findAll(pageable);
            if(!users.isEmpty()){
                return users.map(userMapper::toDto);

            }
             return Page.empty();
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all users", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public List<UserDto> getUsersInWorkspace(Long workspaceId) {
        try {
            log.info("Finding Users with workspace id {}",workspaceId);
            List<User> users = userRepository.findUsersByWorkspaceId(workspaceId);
            if(!users.isEmpty()){
                return users.stream()
                        .map(userMapper::toDto)
                        .peek(userDto -> userDto.setResponseInfo(buildSuccessInfo()))
                        .toList();
            }else {
                return Collections.emptyList();
            }
        } catch(Exception ex) {
            log.error("Unexpected error occurred while finding users with workspace ID {}", workspaceId);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public long getNumberOfUsersInWorkspace(Long workspaceId) {
        try {
            log.info("Counting users numbers with workspace id {}",workspaceId);
            return userRepository.countUsersByWorkspaceId(workspaceId);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while counting users numbers with workspace ID {}", workspaceId);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public RoleDto assignRoleToUser(Long userId, Long roleId) {
        try {
            User user = userMapper.toEntity(findUserById(userId));
            Role role = roleMapper.toEntity(roleService.findRoleById(roleId));
            user.getRoles().add(role);
            userRepository.save(user);
            RoleDto roleDto = roleMapper.toDto(role);
            roleDto.setResponseInfo(buildSuccessInfo());
            return roleDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while assigning the role to User", ex);
                throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    SAVE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
                );
        }
    }
    @Transactional
    public void changeUserStatus(Long userId,Boolean status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new TechnicalException(
                        NOT_FOUND_ERROR_CODE,
                        NOT_FOUND_ERROR_DESCRIPTION,
                        HttpStatus.NOT_FOUND));
        user.setEnabled(status);
        userRepository.save(user);
    }

    @Transactional
    public UserDto updateUser(UserDto userDto) {
        try {
            log.info("Updating user with id {} ..", userDto.getId());
            User existingUser = userRepository.findById(userDto.getId())
                    .orElseThrow(() -> new TechnicalException(
                            NOT_FOUND_ERROR_CODE,
                            NOT_FOUND_ERROR_DESCRIPTION,
                            HttpStatus.NOT_FOUND));
            userMapper.updateFromDto(userDto, existingUser);
            userRepository.save(existingUser);
            userDto.setResponseInfo(buildSuccessInfo());
            return userDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating user with ID {}", userDto.getId(),ex);
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    UPDATE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public ErrorResponse deleteUser(Long userId) {
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            log.info("Deleting user with ID {}", userId);
            userRepository.deleteById(findUserById(userId).getId());
            errorResponse.setResponseInfo(buildSuccessInfo());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting user with ID {}", userId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    DELETE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return errorResponse;
    }
}