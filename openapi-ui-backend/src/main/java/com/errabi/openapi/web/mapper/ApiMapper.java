package com.errabi.openapi.web.mapper;

import com.errabi.openapi.entities.Api;
import com.errabi.openapi.web.model.ApiDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiMapper {
    Api toEntity(ApiDto apiDto);
    ApiDto toDto(Api api);
    void updateFromDto(ApiDto apiDto, @MappingTarget Api api);
}
