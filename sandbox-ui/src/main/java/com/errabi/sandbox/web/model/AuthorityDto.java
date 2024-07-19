package com.errabi.sandbox.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AuthorityDto extends AbstractMessageDto {
    private Long id;
    @NotEmpty(message = "Please add permission")
    private String permission;
    @JsonIgnore
    private Set<RoleDto> roles;
}
