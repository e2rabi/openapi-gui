package com.errabi.openapi.web.controller;

import com.errabi.openapi.exception.ErrorResponse;
import com.errabi.openapi.services.RoleService;
import com.errabi.openapi.web.model.RoleDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sandbox-api/v1/")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/roles")
    public ResponseEntity<RoleDto> createRole(@RequestBody @Valid RoleDto roleDto){
        return new ResponseEntity<>(roleService.createRole(roleDto), HttpStatus.CREATED);
    }

    @GetMapping("/roles/{roleId}")
    @Cacheable(value = "sandbox", key = "#roleId")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable  Long roleId){
        return new ResponseEntity<>(roleService.findRoleById(roleId), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> rolesDto = roleService.findAllRoles();
        return new ResponseEntity<>(rolesDto, HttpStatus.OK);
    }

    @PutMapping("/roles/{roleId}/authorities/{authorityId}")
    public ResponseEntity<RoleDto> assignRoleToUser(@PathVariable Long roleId, @PathVariable Long authorityId ){
        return new ResponseEntity<>(roleService.assignAuthorityToRole(roleId, authorityId), HttpStatus.OK);
    }

    @PutMapping("/roles")
    public ResponseEntity<RoleDto> updateRole(@RequestBody @Valid RoleDto roleDto) {
        return new ResponseEntity<>(roleService.updateRole(roleDto), HttpStatus.OK);
    }

    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity<ErrorResponse> deleteRole(@PathVariable Long roleId){
        return new ResponseEntity<>(roleService.deleteRole(roleId), HttpStatus.OK);
    }
}
