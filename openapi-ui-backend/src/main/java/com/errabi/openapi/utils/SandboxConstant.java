package com.errabi.openapi.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SandboxConstant {
    public static final String SUCCESS_CODE="000";
    public static final String SUCCESS_CODE_DESCRIPTION="SUCCESS";
    public static final String SYSTEM_ERROR = "999";
    public static final String SYSTEM_ERROR_DESCRIPTION = "SYSTEM ERROR";
    public static final String NOT_FOUND_ERROR_CODE = "998";
    public static final String NOT_FOUND_ERROR_DESCRIPTION = "NOT FOUND ERROR";
    public static final String SAVE_ERROR_CODE = "997";
    public static final String SAVE_ERROR_DESCRIPTION = "SAVE ERROR";
    public static final String UPDATE_ERROR_CODE = "996";
    public static final String UPDATE_ERROR_DESCRIPTION = "UPDATE ERROR";
    public static final String DELETE_ERROR_CODE = "995";
    public static final String DELETE_ERROR_DESCRIPTION = "DELETE ERROR";
    public static final String BEAN_VALIDATION_ERROR_CODE="994";
    public static final String USER_ALREADY_EXISTS_ERROR_CODE = "993";
    public static final String USER_ALREADY_EXISTS_ERROR_DESCRIPTION="User already exists";
    public static final String INVALID_USERNAME_OR_PASSWORD_CODE = "992";
    public static final String INVALID_USERNAME_OR_PASSWORD_DESCRIPTION = "Invalid username or password";
    public static final String TOKEN_GENERATION_ERROR_CODE = "991";
    public static final String TOKEN_GENERATION_ERROR_DESCRIPTION = "TOKEN GENERATION ERROR";
    public static final String MAIL_SENDING_ERROR_DESCRIPTION = "FAILED TO SEND THE EMAIL";

    public static final String FILTER_QUERY_ALL = "all";
    public static final String FILTER_QUERY_INACTIVE = "inactive";
    public static final String FILTER_QUERY_EXPIRED = "expired";
}
