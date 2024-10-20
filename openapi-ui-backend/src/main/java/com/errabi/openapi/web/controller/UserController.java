package com.errabi.openapi.web.controller;

import com.errabi.openapi.exception.ErrorResponse;
import com.errabi.openapi.services.UserService;
import com.errabi.openapi.utils.TokenResponse;
import com.errabi.openapi.web.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CreateUserDto> createUser(@RequestBody @Valid CreateUserDto createUserDto){
        return new ResponseEntity<>(userService.createUser(createUserDto), HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/users/query")
    public ResponseEntity<Page<UserDto>> getUsersByFilter(@RequestParam(required = false) String status,
                                                   @RequestParam(required = false) String email, @RequestParam(required = false) String userName,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int pageSize){
        return new ResponseEntity<>(userService.findUsersByFilter(status,email,userName,PageRequest.of(page,pageSize)), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserDto>> getAllUsers( @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(userService.findAllUsers(PageRequest.of(page,pageSize)), HttpStatus.OK);
    }

    @GetMapping("/users/workspace/{workspaceId}")
    public ResponseEntity<List<UserDto>> getUsersByWorkspaceId(@PathVariable Long workspaceId) {
        List<UserDto> usersDto = userService.getUsersInWorkspace(workspaceId);
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PutMapping("/users/{userId}/roles/{roleId}")
    public ResponseEntity<RoleDto> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId ){
        return new ResponseEntity<>(userService.assignRoleToUser(userId, roleId), HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<UpdateUserDto> updateUser(@RequestBody @Valid UpdateUserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ErrorResponse> deleteUser(@PathVariable Long userId){
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<Void> changeUserStatus(@PathVariable Long userId, @RequestParam(defaultValue = "false") Boolean status){
        userService.changeUserStatus(userId, status);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
