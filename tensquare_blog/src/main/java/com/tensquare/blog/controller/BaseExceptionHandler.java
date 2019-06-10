package com.tensquare.blog.controller;

import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {
	
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response error(Exception e){
        e.printStackTrace();
        return new Response(false, StatusCode.ERROR, "执行出错");
    }
}
