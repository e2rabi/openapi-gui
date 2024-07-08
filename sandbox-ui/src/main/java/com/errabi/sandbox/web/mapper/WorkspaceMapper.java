package com.errabi.sandbox.web.mapper;

import com.errabi.sandbox.entities.Workspace;
import com.errabi.sandbox.web.model.WorkspaceDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkspaceMapper {
    Workspace toEntity(WorkspaceDto workspaceDto);
    WorkspaceDto toDto(Workspace workspace);
    void updateFromDto(WorkspaceDto workspaceDto,@MappingTarget Workspace workspace);
}
