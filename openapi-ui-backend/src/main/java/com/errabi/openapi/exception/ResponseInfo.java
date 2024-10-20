package com.errabi.openapi.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseInfo {
    private String errorCode;
    private String errorDescription ;
    private HttpStatus httpStatus;
}
