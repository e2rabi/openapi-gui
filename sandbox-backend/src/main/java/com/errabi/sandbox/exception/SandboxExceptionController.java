package com.errabi.sandbox.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

import static com.errabi.sandbox.utils.SandboxConstant.*;

@RestControllerAdvice
public class SandboxExceptionController {
    @ExceptionHandler(value = {TechnicalException.class})
    public ResponseEntity<ErrorResponse> handleTechnicalException(TechnicalException technicalException) {

        ResponseInfo responseInfo = ResponseInfo.builder()
                .errorCode(technicalException.getErrorCode())
                .errorDescription(technicalException.getErrorDescription())
                .httpStatus(technicalException.getHttpStatus())
                .build();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setResponseInfo(responseInfo);

        return new ResponseEntity<>(errorResponse, responseInfo.getHttpStatus());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

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

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setResponseInfo(responseInfo);

        return new ResponseEntity<>(errorResponse, responseInfo.getHttpStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ResponseInfo responseInfo = ResponseInfo.builder()
                .errorCode(SYSTEM_ERROR)
                .errorDescription(ex.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setResponseInfo(responseInfo);

        return new ResponseEntity<>(errorResponse, responseInfo.getHttpStatus());
    }
}
