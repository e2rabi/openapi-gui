package com.errabi.openapi.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {
    private String errorCode;
    private String errorDescription ;
    private String token;
}
