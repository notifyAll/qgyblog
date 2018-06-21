package com.qiugaoyang.qgyblog.common.exception;

import com.qiugaoyang.qgyblog.common.enums.ResultEnums;

public class UserIsExistException extends BaseException{
    public UserIsExistException(ResultEnums resultEnums) {
        super(resultEnums);
    }
}
