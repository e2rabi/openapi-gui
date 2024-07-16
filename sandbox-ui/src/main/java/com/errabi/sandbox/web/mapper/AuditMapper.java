package com.errabi.sandbox.web.mapper;

import com.errabi.sandbox.entities.Audit;
import com.errabi.sandbox.web.model.AuditDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditMapper {
    Audit toEntity(AuditDto auditDto);
    AuditDto toDto(Audit audit);
    void updateFromDto(AuditDto auditDto,@MappingTarget Audit audit);
}
