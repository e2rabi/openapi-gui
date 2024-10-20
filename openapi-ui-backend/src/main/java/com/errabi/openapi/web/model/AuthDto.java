package com.errabi.openapi.web.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

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
