package com.errabi.sandbox.web.mapper;

import com.errabi.sandbox.entities.Authority;
import com.errabi.sandbox.entities.Role;
import com.errabi.sandbox.entities.User;
import com.errabi.sandbox.web.model.AuthorityDto;
import com.errabi.sandbox.web.model.RoleDto;
import com.errabi.sandbox.web.model.UserDto;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User toEntity(UserDto userDto);

    @Mapping(target = "roles", ignore = true)
    UserDto toDto(User user);

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
