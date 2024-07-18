package com.errabi.sandbox.utils;

import com.errabi.sandbox.exception.ResponseInfo;
import lombok.experimental.UtilityClass;

import static com.errabi.sandbox.utils.SandboxConstant.SUCCESS_CODE;

@UtilityClass
public class SandboxUtils {

    public static ResponseInfo buildSuccessInfo(){
        return ResponseInfo.builder()
                .errorCode(SUCCESS_CODE)
                .errorDescription("Success")
                .build();
    }
}
