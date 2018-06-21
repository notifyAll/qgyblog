package com.qiugaoyang.qgyblog.common.exception;

import com.qiugaoyang.qgyblog.common.enums.ResultEnums;

//用户状态异常
public class UserStateException extends BaseException {
    public UserStateException(ResultEnums resultEnums) {
        super(resultEnums);
    }
}
