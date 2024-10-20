package com.errabi.openapi.web.mapper;

import com.errabi.openapi.entities.Release;
import com.errabi.openapi.web.model.ReleaseDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReleaseMapper {
    Release toEntity(ReleaseDto releaseDto);
    ReleaseDto toDto(Release release);
    void updateFromDto(ReleaseDto releaseDto, @MappingTarget Release release);
}
