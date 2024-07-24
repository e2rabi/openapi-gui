package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Role;
import com.errabi.sandbox.entities.User;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.UserRepository;
import com.errabi.sandbox.web.mapper.RoleMapper;
import com.errabi.sandbox.web.mapper.UserMapper;
import com.errabi.sandbox.web.model.AuthDto;
import com.errabi.sandbox.web.model.RoleDto;
import com.errabi.sandbox.web.model.UserDto;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    public String userLogin(AuthDto userDto){
        Optional<User> userOptional =  userRepository.findByUsername(userDto.getUsername());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
                try {
                    return jwtTokenService.generateToken(user);
                } catch (JOSEException e) {
                    throw new TechnicalException(TOKEN_GENERATION_ERROR_CODE,
                            TOKEN_GENERATION_ERROR_DESCRIPTION,
                            HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else{
                throw new TechnicalException(INVALID_USERNAME_OR_PASSWORD_CODE,
                        INVALID_USERNAME_OR_PASSWORD_DESCRIPTON,
                        HttpStatus.UNAUTHORIZED);
            }
        }else {
            throw new TechnicalException(INVALID_USERNAME_OR_PASSWORD_CODE,
                    INVALID_USERNAME_OR_PASSWORD_DESCRIPTON,
                    HttpStatus.UNAUTHORIZED);
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
            User user = userMapper.toEntity(userDto);
            userRepository.save(user);
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

    public List<UserDto> findAllUsers() {
        try {
            log.info("Fetching all users...");
            List<User> users = userRepository.findAll();
            if(!users.isEmpty()){
                return users.stream().map(userMapper::toDto).toList();
            }else{
                return Collections.emptyList();
            }
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all users", ex);
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

    public UserDto updateUser(UserDto userDto) {
        try {
            log.info("Updating user {} ..", userDto.getId());
            User existingUser = userMapper.toEntity(findUserById(userDto.getId()));
            userMapper.updateFromDto(userDto, existingUser);
            userRepository.save(existingUser);
            userDto.setResponseInfo(buildSuccessInfo());
            return userDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating user with ID {}", userDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    UPDATE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public void deleteUser(Long userId) {
        try {
            log.info("Deleting user with ID {}", userId);
            userRepository.deleteById(findUserById(userId).getId());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting user with ID {}", userId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    DELETE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}