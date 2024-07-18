package com.errabi.sandbox.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDto {
    private Long id;
    @NotEmpty(message = "Please add permission")
    private String permission;
    @JsonIgnore
    private Set<RoleDto> roles;
}
