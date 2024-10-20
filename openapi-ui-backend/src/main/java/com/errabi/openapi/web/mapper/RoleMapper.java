package com.errabi.openapi.web.mapper;

import com.errabi.openapi.entities.Role;
import com.errabi.openapi.web.model.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    @Mapping(target = "users", ignore = true)
    Role toEntity(RoleDto roleDto);

    @Mapping(target = "users", ignore = true)
    RoleDto toDto(Role role);

    @Mapping(target = "users", ignore = true)
    void updateFromDto(RoleDto roleDto, @MappingTarget Role role);
}
