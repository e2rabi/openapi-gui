package com.errabi.openapi.web.mapper;

import com.errabi.openapi.entities.Configuration;
import com.errabi.openapi.web.model.ConfigurationDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConfigurationMapper {
    Configuration toEntity(ConfigurationDto configurationDto);
    ConfigurationDto toDto(Configuration configuration);
    void updateFromDto(ConfigurationDto configurationDto, @MappingTarget Configuration configuration);
}
