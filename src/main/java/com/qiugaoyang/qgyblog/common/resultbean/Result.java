package com.qiugaoyang.qgyblog.common.resultbean;


import com.qiugaoyang.qgyblog.common.enums.ResultEnums;

/**
 * http 请求返回数据 封装
 * @param <T> 返回数据类型
 */
public class Result<T> {

//    错误码
    private Integer code;

//    提示信息
    private String msg;

//    具体内容
    private T data;

    public Result(ResultEnums resultEnums, T data) {
        this.code = resultEnums.getCode();
        this.msg = resultEnums.getMsg();
        this.data = data;
    }

    public Result(ResultEnums resultEnums) {
        this(resultEnums,null);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
