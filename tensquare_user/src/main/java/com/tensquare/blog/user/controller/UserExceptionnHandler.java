package com.tensquare.blog.user.controller;

import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *  统一异常处理类
 *
 * @auther alan.chen
 * @time 2019/6/8 7:54 PM
 */
@Slf4j
@ControllerAdvice
public class UserExceptionnHandler {


    @ExceptionHandler(value = Exception.class)
    public Response error(Exception e) {
        log.error("全局统一异常处理", e);
        return new Response(false, StatusCode.ERROR, e.getMessage());
    }

}
