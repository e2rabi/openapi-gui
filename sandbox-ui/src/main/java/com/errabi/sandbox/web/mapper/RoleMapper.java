package com.errabi.sandbox.web.mapper;

import com.errabi.sandbox.entities.Role;
import com.errabi.sandbox.web.model.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    Role toEntity(RoleDto roleDto);
    RoleDto toDto(Role role);
    void updateFromDto(RoleDto roleDto, @MappingTarget Role role);
}
