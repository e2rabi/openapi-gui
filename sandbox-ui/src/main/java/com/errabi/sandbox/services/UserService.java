package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Role;
import com.errabi.sandbox.entities.User;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.UserRepository;
import com.errabi.sandbox.web.mapper.RoleMapper;
import com.errabi.sandbox.web.mapper.UserMapper;
import com.errabi.sandbox.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.errabi.sandbox.utils.SandboxConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @Transactional
    public UserDto createUser(UserDto userDto){
        try {
            log.info("Creating User {} ..", userDto.getUsername());
            User user = userMapper.toEntity(userDto);
            userRepository.save(user);
            return userDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the User", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    "Unexpected error occurred while saving the User",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public UserDto findUserById(Long userId) {
        log.info("Finding User with id {}",userId);
        Optional<User> optionalUser =  userRepository.findById(userId);
        if(optionalUser.isPresent()){
            return userMapper.toDto(optionalUser.get());
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No User found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<UserDto> findAllUsers() {
        try {
            log.info("Fetching all users...");
            List<User> users = userRepository.findAll();
            return users.stream().map(userMapper::toDto).toList();
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all users", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public UserDto assignRoleToUser(Long userId, Long roleId) {
        try {
            User user = userMapper.toEntity(findUserById(userId));
            Role role = roleMapper.toEntity(roleService.findRoleById(roleId));
            user.getRoles().add(role);
            userRepository.save(user);
            return userMapper.toDto(user);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while assigning the role to User", ex);
                throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
                );
        }
    }

    public UserDto updateUser(UserDto userDto) {
        try {
            log.info("Updating user {} ..", userDto.getId());
            User existingUser = userMapper.toEntity(findUserById(userDto.getId()));
            userMapper.updateFromDto(userDto, existingUser);
            User updatedUser = userRepository.save(existingUser);
            return userMapper.toDto(updatedUser);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating user with ID {}", userDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    "Unexpected error occurred while updating the user",
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
                    "Unexpected error occurred while deleting the user",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}