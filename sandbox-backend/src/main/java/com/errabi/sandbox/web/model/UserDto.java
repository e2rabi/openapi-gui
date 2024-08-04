package com.errabi.sandbox.web.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserDto extends AbstractMessageDto {
    private Long id;
    @NotEmpty(message = "Please add username")
    private  String username;
    @NotEmpty(message = "Please add firstname")
    private  String firstName ;
    @NotEmpty(message = "Please add lastname")
    private  String lastName ;
    private  Boolean enabled ;
    @Email(message = "Please add a valid email")
    private  String email ;
    @NotEmpty(message = "Please add password")
    private  String password;
    @NotEmpty(message = "Please add phone number")
    private  String phone ;
    @NotEmpty(message = "Please add the address")
    private  String address ;
    @NotNull(message = "Please add account status")
    private  boolean accountNonExpired;
    @NotNull(message = "Please add account status")
    private  boolean accountNonLocked;
    @NotNull(message = "Please add the credential")
    private  boolean credentialsNonExpired;
    private Set<RoleDto> roles;
    private Long workspaceId;
}
