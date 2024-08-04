package com.errabi.sandbox.web.controller;

import com.errabi.sandbox.exception.ErrorResponse;
import com.errabi.sandbox.services.UserService;
import com.errabi.sandbox.utils.TokenInfo;
import com.errabi.sandbox.utils.TokenResponse;
import com.errabi.sandbox.web.model.AuthDto;
import com.errabi.sandbox.web.model.RoleDto;
import com.errabi.sandbox.web.model.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.errabi.sandbox.utils.SandboxConstant.*;

@RestController
@RequestMapping("/sandbox-api/v1/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody AuthDto userDto){
        return new ResponseEntity<>(userService.userLogin(userDto), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}")
    @Cacheable(value = "sandbox", key = "#userId")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> usersDto = userService.findAllUsers();
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @GetMapping("/users/workspace/{workspaceId}")
    public ResponseEntity<List<UserDto>> getUsersByWorkspaceId(@PathVariable Long workspaceId) {
        List<UserDto> usersDto = userService.getUsersInWorkspace(workspaceId);
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @GetMapping("/users/workspace/{workspaceId}/count")
    @Cacheable(value = "sandbox", key = "#workspaceId")
    public ResponseEntity<Long> getNumberOfUsersInWorkspace(@PathVariable Long workspaceId) {
        long count = userService.getNumberOfUsersInWorkspace(workspaceId);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/users/{userId}/roles/{roleId}")
    public ResponseEntity<RoleDto> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId ){
        return new ResponseEntity<>(userService.assignRoleToUser(userId, roleId), HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ErrorResponse> deleteUser(@PathVariable Long userId){
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }
}
