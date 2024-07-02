package com.errabi.sandbox.web.mapper;

import com.errabi.sandbox.entities.Release;
import com.errabi.sandbox.web.model.ReleaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReleaseMapper {
    Release toEntity(ReleaseDto releaseDto);
    ReleaseDto toDto(Release release);
    void updateFromDto(ReleaseDto releaseDto, @MappingTarget Release release);
}
