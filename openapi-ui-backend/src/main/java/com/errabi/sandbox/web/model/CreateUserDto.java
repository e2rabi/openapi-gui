package com.errabi.sandbox.web.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CreateUserDto extends AbstractMessageDto{
    @NotEmpty(message = "Username is required")
    private  String username;
    @NotEmpty(message = "Firstname is required")
    private  String firstName ;
    @NotEmpty(message = "Lastname is required")
    private  String lastName ;
    private  Boolean enabled = false;
    private  Boolean firstLoginChangePassword ;
    @Email(message = "Invalid email")
    private  String email ;
    private  String password;
    @NotEmpty(message = "Phone is required")
    @Pattern(regexp = "\\d+", message = "Phone must contain only numbers")
    private  String phone ;
    private  String temporaryPassword;
    @Builder.Default
    private  Boolean accountNonExpired = true;
    @Builder.Default
    private  Boolean accountNonLocked = true;
    @Builder.Default
    private  Boolean credentialsNonExpired = true;
    private String expiryDate ;
    private Long workspaceId;
}
