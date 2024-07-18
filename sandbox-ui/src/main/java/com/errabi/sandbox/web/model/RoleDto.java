package com.errabi.sandbox.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;
    @NotEmpty(message = "Please add role name")
    private String name;
    @JsonIgnore
    private Set<UserDto> users;
    private Set<AuthorityDto> authorities;
}
