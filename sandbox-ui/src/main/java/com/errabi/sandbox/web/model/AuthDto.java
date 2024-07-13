package com.errabi.sandbox.web.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
    @NotEmpty(message = "Please add username")
    private  String username;
    @NotEmpty(message = "Please add password")
    private  String password;
}
