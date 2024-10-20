package com.errabi.openapi.services;

import com.errabi.openapi.entities.Role;
import com.errabi.openapi.entities.User;
import com.errabi.openapi.entities.Workspace;
import com.errabi.openapi.exception.ErrorResponse;
import com.errabi.openapi.exception.TechnicalException;
import com.errabi.openapi.repositories.UserRepository;
import com.errabi.openapi.repositories.WorkspaceRepository;
import com.errabi.openapi.utils.TokenInfo;
import com.errabi.openapi.utils.TokenResponse;
import com.errabi.openapi.web.mapper.RoleMapper;
import com.errabi.openapi.web.mapper.UserMapper;
import com.errabi.openapi.web.model.*;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static com.errabi.openapi.utils.SandboxConstant.*;
import static com.errabi.openapi.utils.SandboxUtils.buildSuccessInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
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

    private void validateUser(CreateUserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            log.error("Failed to save user. Username {} already exists.", userDto.getUsername());
            throw new TechnicalException(
                    USER_ALREADY_EXISTS_ERROR_CODE,
                    USER_ALREADY_EXISTS_ERROR_DESCRIPTION,
                    HttpStatus.CONFLICT);
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            log.error("Failed to save user. email {} already exists.", userDto.getEmail());
            throw new TechnicalException(
                    USER_ALREADY_EXISTS_ERROR_CODE,
                    USER_ALREADY_EXISTS_ERROR_DESCRIPTION,
                    HttpStatus.CONFLICT);
        }
    }
    @Transactional
    public CreateUserDto createUser(CreateUserDto createUserDto){
        log.info("Creating User {} ..", createUserDto.getUsername());
        validateUser(createUserDto);
        try {
            String temporaryPassword = TemporaryPasswordGenerator.generatePassword();
            createUserDto.setPassword(StringUtils.isEmpty(createUserDto.getPassword())?passwordEncoder.encode(temporaryPassword):
                    passwordEncoder.encode(createUserDto.getPassword()));

            User user = userRepository.save(userMapper.toEntity(createUserDto));

            if(createUserDto.getWorkspaceId()!=null) {
                assignExistingWorkspaceToUser(user,createUserDto.getWorkspaceId());
            }
            createUserDto = userMapper.toCreateUserDto(user,temporaryPassword);
            createUserDto.setResponseInfo(buildSuccessInfo());

            return createUserDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the User", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    SAVE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
    @Transactional
    public Page<UserDto> findUsersByFilter(String status, String email, String username, Pageable pageable){
        log.info("find users by query ...");
        User userProb =  User.builder()
                .email(StringUtils.isEmpty(email)?null:email)
                .enabled(FILTER_QUERY_ALL.equalsIgnoreCase(status)||FILTER_QUERY_EXPIRED.equalsIgnoreCase(status)?null: !FILTER_QUERY_INACTIVE.equalsIgnoreCase(status))
                .username(StringUtils.isEmpty(username)?null:username)
                .accountNonExpired(FILTER_QUERY_EXPIRED.equalsIgnoreCase(status)?false:null)
                .build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id","version");
        Example<User> example = Example.of(userProb, matcher);

        Pageable sortedByCreatedDate = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("created").descending());
        return userRepository.findAll(example,sortedByCreatedDate)
                      .map(userMapper::toDto);

    }

    @Transactional(readOnly = true)
    public UserDto findUserById(Long userId) {
        log.info("Finding User with id {}", userId);
        return userRepository.findById(userId)
                .map(user -> {
                    UserDto userDto = userMapper.toDto(user);
                    userDto.setResponseInfo(buildSuccessInfo());
                    return userDto;
                })
                .orElseThrow(() -> new TechnicalException(
                        NOT_FOUND_ERROR_CODE,
                        NOT_FOUND_ERROR_DESCRIPTION,
                        HttpStatus.NOT_FOUND));
    }
   @Transactional(readOnly = true)
    public Page<UserDto> findAllUsers(Pageable pageable) {
       log.info("Fetching all users...");
       try {
           Page<User> users = userRepository.findAll(pageable);
           return users.isEmpty() ? Page.empty() : users.map(userMapper::toDto);
       } catch (Exception ex) {
           log.error("Unexpected error occurred while fetching all users", ex);
           throw new TechnicalException(
                   SYSTEM_ERROR,
                   SYSTEM_ERROR_DESCRIPTION,
                   HttpStatus.INTERNAL_SERVER_ERROR
           );
       }
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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
    }

    @Transactional
    public void assignExistingWorkspaceToUser(User user, Long workspaceId) {
        if (workspaceId != null) {
            Workspace newWorkspace = workspaceRepository.findById(workspaceId)
                    .orElseThrow(() -> new TechnicalException(
                            NOT_FOUND_ERROR_CODE,
                            NOT_FOUND_ERROR_DESCRIPTION,
                            HttpStatus.NOT_FOUND));

            if (user.getWorkspace() != null) {
                Workspace currentWorkspace = workspaceRepository.findById(user.getWorkspace().getId())
                        .orElseThrow(() -> new TechnicalException(
                                NOT_FOUND_ERROR_CODE,
                                NOT_FOUND_ERROR_DESCRIPTION,
                                HttpStatus.NOT_FOUND));

                currentWorkspace.getUsers().remove(user);
                workspaceRepository.save(currentWorkspace);
            }

            newWorkspace.getUsers().add(user);
            user.setWorkspace(newWorkspace);
            workspaceRepository.save(newWorkspace);
            userRepository.save(user);
        }
    }
    @Transactional
    public UpdateUserDto updateUser(UpdateUserDto updateRequest) {
        try {
            log.info("Updating user with id {} ..", updateRequest.getId());
            User user = userRepository.findById(updateRequest.getId()).orElseThrow(() ->  new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND));
           // update user workspace
            if(updateRequest.getWorkspace()!=null){
                assignExistingWorkspaceToUser(user,updateRequest.getWorkspace().getId());
            }
            // update user data
            user.setFirstName(updateRequest.getFirstName());
            user.setLastName(updateRequest.getLastName());
            user.setPhone(updateRequest.getPhone());
            user.setExpiryDate(LocalDate.parse(updateRequest.getExpiryDate()));

            updateRequest.setResponseInfo(buildSuccessInfo());
            return updateRequest;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating user with ID {}", updateRequest.getId(),ex);
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
            log.error("Unexpected error occurred while deleting user with ID {}", userId,ex);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    DELETE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return errorResponse;
    }
}