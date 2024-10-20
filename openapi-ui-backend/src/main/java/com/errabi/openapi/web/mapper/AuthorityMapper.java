package com.errabi.openapi.web.mapper;

import com.errabi.openapi.entities.Authority;
import com.errabi.openapi.web.model.AuthorityDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorityMapper {
    @Mapping(target = "roles", ignore = true)
    Authority toEntity(AuthorityDto authorityDto);

    @Mapping(target = "roles", ignore = true)
    AuthorityDto toDto(Authority authority);

    @Mapping(target = "roles", ignore = true)
    void updateFromDto(AuthorityDto authorityDto, @MappingTarget Authority authority);
}
