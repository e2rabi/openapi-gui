package com.errabi.sandbox.web.mapper;

import com.errabi.sandbox.entities.Module;
import com.errabi.sandbox.web.model.ModuleDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModuleMapper {
    Module toEntity(ModuleDto moduleDto);
    ModuleDto toDto(Module module);
    void updateFromDto(ModuleDto moduleDto, @MappingTarget Module module);
}
