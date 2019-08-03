package com.tensquare.comment.controller;

import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@Slf4j
@ControllerAdvice
public class BaseExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Response error(Exception e){
		log.error("统一异常处理： ", e);
		return new Response(false, StatusCode.REPERROR, e.getMessage());
	}
}
