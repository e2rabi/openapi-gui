package com.errabi.openapi.web.mapper;

import com.errabi.openapi.entities.openapi.Server;
import com.errabi.openapi.web.model.ServerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServerMapper {

    @Mapping(target = "id",ignore = true)
    Server toEntity(ServerModel serverModel);
    ServerModel toModel(Server server);
}
