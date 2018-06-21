package com.qiugaoyang.qgyblog.common.exception;

import com.qiugaoyang.qgyblog.common.enums.ResultEnums;

public class BaseException extends RuntimeException{
    private Integer code;

    public BaseException(ResultEnums resultEnums) {
        super(resultEnums.getMsg());
        this.code = resultEnums.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
