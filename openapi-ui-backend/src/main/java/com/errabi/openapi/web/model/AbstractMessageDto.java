package com.errabi.openapi.web.model;

import com.errabi.openapi.exception.ResponseInfo;
import lombok.Data;

@Data
public class AbstractMessageDto {
    protected ResponseInfo responseInfo;
}
