package com.errabi.sandbox.web.mapper;

import com.errabi.sandbox.entities.Api;
import com.errabi.sandbox.web.model.ApiDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiMapper {
    Api toEntity(ApiDto apiDto);
    ApiDto toDto(Api api);
    void updateFromDto(ApiDto apiDto, @MappingTarget Api api);
}
