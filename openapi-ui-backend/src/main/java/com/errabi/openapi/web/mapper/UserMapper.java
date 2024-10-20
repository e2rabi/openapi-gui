package com.errabi.openapi.web.mapper;

import com.errabi.openapi.entities.Authority;
import com.errabi.openapi.entities.Role;
import com.errabi.openapi.entities.User;
import com.errabi.openapi.entities.Workspace;
import com.errabi.openapi.web.model.*;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User toEntity(UserDto userDto);

    User toEntity(CreateUserDto userDto);

    @Mapping(target = "temporaryPassword",source = "temporaryPassword")
    @Mapping(target = "password",ignore = true)
    CreateUserDto toCreateUserDto(User user,String temporaryPassword);

    @Mapping(expression = "java(toWorkspaceDto(user.getWorkspace()))", target = "workspace")
    @Mapping(target = "roles", ignore = true)
    UserDto toDto(User user);

    default WorkspaceDto toWorkspaceDto(Workspace workspace){
        if(workspace == null ){
            return null;
        }
        return WorkspaceDto.builder()
                .id(workspace.getId())
                .name(workspace.getName())
                .build();
    }
    @Mapping(target = "roles", ignore = true)
    void updateFromDto(UserDto userDto, @MappingTarget User user);

    @AfterMapping
    default void handleRoles(User user, @MappingTarget UserDto userDto) {
        if (user.getRoles() != null) {
            userDto.setRoles(user.getRoles().stream()
                    .map(role -> {
                        RoleDto roleDto = new RoleDto();
                        roleDto.setId(role.getId());
                        roleDto.setName(role.getName());
                        roleDto.setAuthorities(role.getAuthorities().stream()
                                .map(authority -> new AuthorityDto(authority.getId(), authority.getPermission(), null))
                                .collect(Collectors.toSet()));
                        return roleDto;
                    }).collect(Collectors.toSet()));
        }
    }

    @AfterMapping
    default void handleRoles(UserDto userDto, @MappingTarget User user) {
        if (userDto.getRoles() != null) {
            user.setRoles(userDto.getRoles().stream()
                    .map(roleDto -> {
                        Role role = new Role();
                        role.setId(roleDto.getId());
                        role.setName(roleDto.getName());
                        role.setAuthorities(roleDto.getAuthorities().stream()
                                .map(authorityDto -> new Authority(authorityDto.getId(), authorityDto.getPermission(), null))
                                .collect(Collectors.toSet()));
                        return role;
                    }).collect(Collectors.toSet()));
        }
    }
}
