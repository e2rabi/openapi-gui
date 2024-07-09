package com.errabi.sandbox.web.mapper;

import com.errabi.sandbox.entities.User;
import com.errabi.sandbox.web.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toEntity(UserDto userDto);
    UserDto toDto(User user);
    void updateFromDto(UserDto userDto, @MappingTarget User user);
}
