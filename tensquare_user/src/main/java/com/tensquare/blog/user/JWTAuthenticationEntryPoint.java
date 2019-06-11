package com.tensquare.blog.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对于spring security的统一没有权限的403信息处理类
 * TODO 后面可以将403重定向到页面
 * TODO 后面可以将token 放入到redis中管理，过期的设置
 *
 * @auther alan.chen
 * @time 2019/6/8 6:26 PM
 */
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        String reason = "统一处理，原因："+authException.getMessage();
        response.getWriter().write(new ObjectMapper().writeValueAsString(reason));
    }

}
