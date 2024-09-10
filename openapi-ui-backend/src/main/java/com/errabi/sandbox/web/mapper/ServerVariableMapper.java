package com.errabi.sandbox.web.mapper;

import com.errabi.sandbox.entities.openapi.ServerVariable;
import com.errabi.sandbox.web.model.ServerVariableModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",uses = {ServerMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServerVariableMapper {

    @Mapping(target = "id",ignore = true)
    ServerVariable toEntity(ServerVariableModel serverVariableModel);

    ServerVariableModel toModel(ServerVariable serverVariable);
}
