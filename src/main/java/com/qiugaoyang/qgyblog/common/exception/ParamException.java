package com.qiugaoyang.qgyblog.common.exception;

import com.qiugaoyang.qgyblog.common.enums.ResultEnums;

//参数异常
public class ParamException extends BaseException{

    public ParamException(ResultEnums resultEnums) {
        super(resultEnums);
    }
}
