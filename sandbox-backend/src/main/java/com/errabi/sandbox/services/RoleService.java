package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Authority;
import com.errabi.sandbox.entities.Role;
import com.errabi.sandbox.exception.ErrorResponse;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.errabi.sandbox.utils.SandboxConstant.*;
import static com.errabi.sandbox.utils.SandboxUtils.buildSuccessInfo;

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
            Role role = roleRepository.save(roleMapper.toEntity(roleDto));
            roleDto = roleMapper.toDto(role);
            roleDto.setResponseInfo(buildSuccessInfo());
            return roleDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while saving the Role", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    SAVE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public RoleDto findRoleById(Long roleId) {
        log.info("Finding Role with id {}",roleId);
        Optional<Role> optionalRole =  roleRepository.findById(roleId);
        if(optionalRole.isPresent()){
            RoleDto roleDto = roleMapper.toDto(optionalRole.get());
            roleDto.setResponseInfo(buildSuccessInfo());
            return roleDto;
        }else{
            throw new TechnicalException(
                    NOT_FOUND_ERROR_CODE,
                    NOT_FOUND_ERROR_DESCRIPTION,
                    HttpStatus.NOT_FOUND);
        }
    }

    public List<RoleDto> findAllRoles() {
        try {
            log.info("Fetching all roles...");
            List<Role> roles = roleRepository.findAll();
            if(!roles.isEmpty()){
                return roles.stream()
                        .map(roleMapper::toDto)
                        .peek(roleDto -> roleDto.setResponseInfo(buildSuccessInfo()))
                        .toList();
            }else{
                return Collections.emptyList();
            }
        } catch(Exception ex) {
            log.error("Unexpected error occurred while fetching all roles", ex);
            throw new TechnicalException(
                    SYSTEM_ERROR,
                    SYSTEM_ERROR_DESCRIPTION,
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
            RoleDto roleDto = roleMapper.toDto(role);
            roleDto.setResponseInfo(buildSuccessInfo());
            return roleDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while assigning the authority to role", ex);
            throw new TechnicalException(
                    SAVE_ERROR_CODE,
                    SAVE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public RoleDto updateRole(RoleDto roleDto) {
        try {
            log.info("Updating role {} ..", roleDto.getId());
            Role existingRole = roleMapper.toEntity(findRoleById(roleDto.getId()));
            roleMapper.updateFromDto(roleDto, existingRole);
            roleRepository.save(existingRole);
            roleDto.setResponseInfo(buildSuccessInfo());
            return roleDto;
        } catch(Exception ex) {
            log.error("Unexpected error occurred while updating role with ID {}", roleDto.getId());
            throw new TechnicalException(
                    UPDATE_ERROR_CODE,
                    UPDATE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Transactional
    public ErrorResponse deleteRole(Long roleId) {
        ErrorResponse errorResponse = new ErrorResponse();
        try {
            log.info("Deleting role with ID {}", roleId);
            roleRepository.deleteById(findRoleById(roleId).getId());
            errorResponse.setResponseInfo(buildSuccessInfo());
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting role with ID {}", roleId);
            throw new TechnicalException(
                    DELETE_ERROR_CODE,
                    DELETE_ERROR_DESCRIPTION,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return errorResponse;
    }
}