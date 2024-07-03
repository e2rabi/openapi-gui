package com.errabi.sandbox.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

import static com.errabi.sandbox.utils.SandboxConstant.BEAN_VALIDATION_ERROR_CODE;

@RestControllerAdvice
public class SandboxExceptionController {
    @ExceptionHandler(value = {TechnicalException.class})
    public ResponseEntity<ResponseInfo> handleTechnicalException(TechnicalException technicalException) {
        ResponseInfo responseInfo = ResponseInfo.builder()
                .errorCode(technicalException.getErrorCode())
                .errorDescription(technicalException.getErrorDescription())
                .httpStatus(technicalException.getHttpStatus())
                .build();
        return new ResponseEntity<>(responseInfo, responseInfo.getHttpStatus());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseInfo> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        List<String> messages = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                    } else {
                        return error.getObjectName() + ": " + error.getDefaultMessage();
                    }
                })
                .toList();

        ResponseInfo responseInfo = ResponseInfo.builder()
                .errorCode(BEAN_VALIDATION_ERROR_CODE)
                .errorDescription(messages.toString())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(responseInfo, responseInfo.getHttpStatus());
    }
}
