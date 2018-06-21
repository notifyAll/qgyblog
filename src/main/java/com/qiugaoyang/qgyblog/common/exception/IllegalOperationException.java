package com.qiugaoyang.qgyblog.common.exception;

import com.qiugaoyang.qgyblog.common.enums.ResultEnums;

/**
 * 非法操作 异常 重要
 */
public class IllegalOperationException extends BaseException{
    public IllegalOperationException(ResultEnums resultEnums) {
        super(resultEnums);
    }
}
