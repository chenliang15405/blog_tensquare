package com.tensquare.blog.user.filter;

import com.tensquare.common.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

/**
 * 认证成功之后， 如果需要调用其他请求，则带有token，认证，然后再调用鉴权filter，security filter 就是 鉴权filter， 通过这个filter进行鉴权
 *
 * @auther alan.chen
 * @time 2019/6/8 12:25 PM
 */
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    private static String jwtKey = null;
    static {
        try {
            Properties prop = PropertiesLoaderUtils.loadAllProperties("application.yml", Thread.currentThread().getContextClassLoader());
            jwtKey = prop.getProperty("key");
        } catch (IOException e) {
            log.error("读取配置文件出错", e);
        }
    }
    private JwtUtil jwtUtil = new JwtUtil();


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        // 获取头信息中的token
        String tokenHeader = request.getHeader(JwtUtil.TOKEN_HEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(JwtUtil.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        super.doFilterInternal(request, response, chain);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        // 去掉token的前缀
        String token = tokenHeader.replace(JwtUtil.TOKEN_PREFIX, "");
        String username = jwtUtil.getUsernameWithSecurityFilter(jwtKey,token);
        String role = jwtUtil.getUserRoleWithSecurityFilter(jwtKey,token);
        if (username != null){
            return new UsernamePasswordAuthenticationToken(username, null,
                                        Collections.singleton(new SimpleGrantedAuthority(role))
            );
        }
        return null;
    }







}
