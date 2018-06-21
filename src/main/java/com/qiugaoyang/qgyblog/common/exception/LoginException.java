package com.qiugaoyang.qgyblog.common.exception;

import com.qiugaoyang.qgyblog.common.enums.ResultEnums;

public class LoginException extends BaseException {
    public LoginException(ResultEnums resultEnums) {
        super(resultEnums);
    }
}
