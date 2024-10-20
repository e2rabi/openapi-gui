package com.errabi.openapi.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class RoleDto extends AbstractMessageDto{
    private Long id;
    @NotEmpty(message = "Please add role name")
    private String name;
    @JsonIgnore
    private Set<UserDto> users;
    private Set<AuthorityDto> authorities;
}
