package com.qiugaoyang.qgyblog.common.exception;

import com.qiugaoyang.qgyblog.common.enums.ResultEnums;

//注册失败
public class RegisterFailException extends BaseException{

    public RegisterFailException(ResultEnums resultEnums) {
        super(resultEnums);
    }
}
