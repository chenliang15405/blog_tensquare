package com.tensquare.common.entity;

/**
 * 状态码枚举
 *
 * @author alan.chen
 * @date 2020/7/31 6:01 PM
 */
public enum StatusCodeEnum {

    /**
     * 返回状态
     */
    OK(20000, "成功"),
    ERROR(20001, "失败"),
    LONGINERROR(20002, "用户名或密码错误"),
    ACCESSERROR(20003, "权限不足"),
    REMOTEERROR(20004, "远程调用失败"),
    REPERROR(20005, "重复操作");


    private int code;
    private String msg;

    StatusCodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
