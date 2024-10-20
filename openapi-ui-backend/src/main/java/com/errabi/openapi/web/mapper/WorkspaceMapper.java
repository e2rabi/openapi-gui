package com.errabi.openapi.web.mapper;

import com.errabi.openapi.entities.Workspace;
import com.errabi.openapi.web.model.WorkspaceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkspaceMapper {
    Workspace toEntity(WorkspaceDto workspaceDto);
    @Mapping(target = "products", ignore = true)
    WorkspaceDto toDto(Workspace workspace);

    void updateFromDto(WorkspaceDto workspaceDto,@MappingTarget Workspace workspace);
}
