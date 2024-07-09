package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Authority;
import com.errabi.sandbox.entities.Role;
import com.errabi.sandbox.exception.TechnicalException;
import com.errabi.sandbox.repositories.RoleRepository;
import com.errabi.sandbox.web.mapper.AuthorityMapper;
import com.errabi.sandbox.web.mapper.RoleMapper;
import com.errabi.sandbox.web.model.RoleDto;
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
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final AuthorityService authorityService;
    private final AuthorityMapper authorityMapper;

    @Transactional
    public RoleDto createRole(RoleDto roleDto){
        try {
            log.info("Creating Role {} ..", roleDto.getName());
            Role role = roleMapper.toEntity(roleDto);
            roleRepository.save(role);
            return roleDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the Role", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    "Unexpected error occurred!",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public RoleDto findRoleById(Long roleId) {
        log.info("Finding Role with id {}",roleId);
        Optional<Role> optionalRole =  roleRepository.findById(roleId);
        if(optionalRole.isPresent()){
            return roleMapper.toDto(optionalRole.get());
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    "No Role found",
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<RoleDto> findAllRoles() {
        try {
            log.info("Fetching all roles...");
            List<Role> roles = roleRepository.findAll();
            return roles.stream().map(roleMapper::toDto).toList();
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all roles", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public RoleDto assignAuthorityToRole(Long roleId, Long authorityId) {
        try {
            Role role = roleMapper.toEntity(findRoleById(roleId));
            Authority authority = authorityMapper.toEntity(authorityService.findAuthorityById(authorityId));
            role.getAuthorities().add(authority);
            roleRepository.save(role);
            return roleMapper.toDto(role);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while assigning the authority to role", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    "Unexpected error occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public RoleDto updateRole(RoleDto roleDto) {
        try {
            log.info("Updating role {} ..", roleDto.getId());
            Role existingRole = roleMapper.toEntity(findRoleById(roleDto.getId()));
            roleMapper.updateFromDto(roleDto, existingRole);
            Role updatedRole = roleRepository.save(existingRole);
            return roleMapper.toDto(updatedRole);
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating role with ID {}", roleDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    "Unexpected error occurred while updating the role",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public void deleteRole(Long roleId) {
        try {
            log.info("Deleting role with ID {}", roleId);
            roleRepository.deleteById(findRoleById(roleId).getId());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting role with ID {}", roleId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    "Unexpected error occurred while deleting the role",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}