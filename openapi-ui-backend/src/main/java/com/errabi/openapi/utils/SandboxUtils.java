package com.errabi.openapi.utils;

import com.errabi.openapi.exception.ResponseInfo;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import static com.errabi.openapi.utils.SandboxConstant.SUCCESS_CODE;
import static com.errabi.openapi.utils.SandboxConstant.SUCCESS_CODE_DESCRIPTION;

@UtilityClass
public class SandboxUtils {
    public static ResponseInfo buildSuccessInfo(){
        return ResponseInfo.builder()
                .errorCode(SUCCESS_CODE)
                .errorDescription(SUCCESS_CODE_DESCRIPTION)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
