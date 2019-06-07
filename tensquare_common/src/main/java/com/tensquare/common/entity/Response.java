package com.tensquare.common.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @auther alan.chen
 * @time 2019/6/4 8:40 PM
 */
@Getter
@Setter
public class Response {

    private boolean flag;
    private Integer code;
    private String message;
    private Object data;


    public Response() {}

    public Response(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Response(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
