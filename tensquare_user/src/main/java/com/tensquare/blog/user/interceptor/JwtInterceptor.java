package com.tensquare.blog.user.interceptor;

import com.tensquare.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther alan.chen
 * @time 2019/6/8 6:50 PM
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 过滤请求
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过拦截器");
        //无论如何都放行，具体操作的权限能不能操作，到具体业务中判断
        //拦截器只是对请求头中的token获取到进行解析

        String header = request.getHeader("Authorization");
        if(header!=null && !"".equals(header)){
            if(header.startsWith("Bearer ")){
                String token = header.substring(7);
                //对令牌进行解析，验证
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    if(claims!=null&&!"".equals(claims)){
                        if("admin".equals(claims.get("roles"))){
                            request.setAttribute("admin_claims",claims);
                        }
                        if("user".equals(claims.get("roles"))){
                            request.setAttribute("admin_user",claims);
                        }
                    }
                } catch (Exception e) {
                    throw  new RuntimeException("令牌不正确！");
                }
            }
        }
        //如果header中没有token也会放行。只不过如果有tokem的头信息，到业务层中就可以获取到具体的信息，然后执行业务操作
        return true;
    }
}
