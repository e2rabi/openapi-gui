package com.errabi.sandbox.web.controller;

import com.errabi.sandbox.services.UserService;
import com.errabi.sandbox.web.model.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sandbox-api/v1/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> usersDto = userService.findAllUsers();
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PutMapping("/users/{userId}/roles/{roleId}")
    public ResponseEntity<UserDto> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId ){
        return new ResponseEntity<>(userService.assignRoleToUser(userId, roleId), HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
