package com.errabi.openapi.web.mapper;

import com.errabi.openapi.entities.Solution;
import com.errabi.openapi.web.model.SolutionDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SolutionMapper {
    Solution toEntity(SolutionDto solutionDto);
    SolutionDto toDto(Solution solution);
    void updateFromDto(SolutionDto solutionDto, @MappingTarget Solution solution);
}
