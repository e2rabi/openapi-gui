package com.errabi.sandbox.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseInfo {
    protected String responseUID ;
    protected String errorCode ;
    protected String errorDescription ;

}
