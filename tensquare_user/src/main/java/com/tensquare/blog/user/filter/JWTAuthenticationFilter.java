package com.tensquare.blog.user.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.tensquare.blog.user.entity.JwtUser;
import com.tensquare.blog.user.entity.LoginAdminUser;
import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import com.tensquare.common.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 * spring security 的权限过滤器，可以自定义
 *  认证过滤器 主要是去进行用户账号的验证
 *
 * @auther alan.chen
 * @time 2019/6/8 12:08 PM
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/admin/login"); // 登录的api
    }

    private ObjectMapper mapper = new ObjectMapper();


    private static String jwtKey = null;
    static {
        try {
            Properties prop = PropertiesLoaderUtils.loadAllProperties("application.yml", Thread.currentThread().getContextClassLoader());
            jwtKey = prop.getProperty("key");
            System.out.println("-------appliction.yml jwt-key: -------" + jwtKey);
        } catch (IOException e) {
            log.error("读取配置文件出错", e);
        }
    }


    private JwtUtil jwtUtil = new JwtUtil();


    /**
     * 登录处理的认证的api
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // 从输入流中获取到登录的信息
        try {
            LoginAdminUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginAdminUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            log.error("获取登录信息失败",e);
            return null;
        }
    }


    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        // 所以就是JwtUser啦
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        System.out.println("jwtUser:" + jwtUser.toString());

        String role = "";
        // 因为在JwtUser中存了权限信息，可以直接获取，由于只有一个角色就这么干了
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        for (GrantedAuthority authority : authorities){
            role = authority.getAuthority();
        }

        String token = jwtUtil.createJWTWithParamsKey(jwtKey,0 ,jwtUser.getId(), jwtUser.getUsername(), role);
        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
        Map<String,String> map = Maps.newHashMap();
//        map.put("token", JwtUtil.TOKEN_PREFIX + token);
        map.put("role", role);

        // 设置token--header
        response.setHeader("token", JwtUtil.TOKEN_PREFIX + token);// token 会放在header中
        // 设置 返回信息
        Response resp = new Response(true, StatusCode.OK, "认证成功",map);
        String respMessage = mapper.writeValueAsString(resp);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respMessage);
    }


    // 这是验证失败时候调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("认证失败，失败原因 ：{} ",  failed.getMessage());
        // response.getWriter().write("authentication failed, reason: " + failed.getMessage());

        // 封装为response的格式返回
        Response resp = new Response(false, StatusCode.LONGINERROR, "认证失败", "authentication failed, reason: " + failed.getMessage());
        String respMessage = mapper.writeValueAsString(resp);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respMessage);
    }


}
