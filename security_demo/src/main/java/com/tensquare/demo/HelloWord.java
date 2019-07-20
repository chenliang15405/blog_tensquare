package com.tensquare.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther alan.chen
 * @time 2019/7/20 2:56 PM
 */
@RestController
public class HelloWord {

	@RequestMapping(value = "/hello")
	public String hello() {
		return "Hello Word";
	}
}
