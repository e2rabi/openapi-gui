package com.errabi.sandbox.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class TechnicalException extends RuntimeException {
    private final String errorCode;
    private final String errorDescription ;
    private final HttpStatus httpStatus;
}
