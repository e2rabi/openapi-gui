package com.errabi.sandbox.web.mapper;

import com.errabi.sandbox.entities.Authority;
import com.errabi.sandbox.web.model.AuthorityDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorityMapper {
    Authority toEntity(AuthorityDto authorityDto);
    AuthorityDto toDto(Authority authority);
    void updateFromDto(AuthorityDto authorityDto, @MappingTarget Authority authority);
}
