package com.errabi.sandbox.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {
    private String errorCode;
    private String errorDescription ;
    private HttpStatus httpStatus;
    private String token;
}
