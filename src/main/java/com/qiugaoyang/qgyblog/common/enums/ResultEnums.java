package com.qiugaoyang.qgyblog.common.enums;

public enum ResultEnums {
    SUCCESS(1,"成功"),
    REQUEST_ERROR(0,"请求失败"),


    PARAM_IS_NULL(10001,"参数不允许为空"),
    LOGIN_FAIL(10002,"帐号或密码错误"),
    USER_IS_EXIST(10003,"用户已经存在"),
    REGISTER_FAIL(10004,"用户注册失败"),
    USER_START_ERROR(10005,"用户为非正常状态 未激活或者封号"),

    ILLEGAL_OPERATION(20000,"非法操作"),
    ILLEGAL_OPERATION_INFRINGEMENT(20001,"非法操作 侵犯他人权益"),
    ILLEGAL_OPERATION_NOT_FIND_DATA(20002,"数据不存在")
    ;

    private Integer code;

    private String msg;

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
