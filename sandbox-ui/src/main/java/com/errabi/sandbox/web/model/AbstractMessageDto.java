package com.errabi.sandbox.web.model;

import com.errabi.sandbox.exception.ResponseInfo;
import lombok.Data;

@Data
public class AbstractMessageDto {
    protected ResponseInfo responseInfo;
}
