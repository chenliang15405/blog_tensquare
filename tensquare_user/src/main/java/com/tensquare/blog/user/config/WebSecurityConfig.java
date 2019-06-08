package com.tensquare.blog.user.config;

import com.tensquare.blog.user.JWTAuthenticationEntryPoint;
import com.tensquare.blog.user.filter.JWTAuthenticationFilter;
import com.tensquare.blog.user.filter.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 *
 * spring security的安全配置类
 *
 * @auther alan.chen
 * @time 2019/6/7 6:41 PM
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 标示可以使用注解进行权限设置
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    // 因为UserDetailsService的实现类实在太多啦，这里设置一下我们要注入的实现类
    @Qualifier("adminUserService")
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }


    //authorizeRequests 表示所有security全注解配置实现的开端，表示开始说明需要配置的权限
    //需要的权限分两部分，第一部分是拦截的路径，第二部分是访问该路径需要的权限
    //anyMatchers()表示拦截匹配的路径，permitAll表示任何权限都可以访问，直接放行所有
    //anyRequest表示任何请求，authenticated表示认证后才可以访问
    //.and().csrf().disable(); 表示拦截csrf攻击
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable()
                .authorizeRequests()
                // admin 资源都是需要认证
                .antMatchers("/admin/**").authenticated()
//                .antMatchers("/**").permitAll()
                // 需要角色为ADMIN才能删除该资源
                .antMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN") // 使用hasRole 则不能带有前缀ROLE_
                .antMatchers(HttpMethod.PUT,"/admin/**").hasRole("ADMIN")
                // 其他请求都不需要验证
                .antMatchers(
                            HttpMethod.GET,
                            "/*.html",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js"
                    ).anonymous()
                // swagger start
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                // swagger end
                .anyRequest().permitAll()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                    // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    // 加一句这个，设置统一处理403信息
                 .and()
                 .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint());
    }



    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
