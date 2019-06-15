package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * 博客前端网关过滤器，将header中的信息转发到微服务中去
 *
 * @auther alan.chen
 * @time 2019/6/15 7:10 PM
 */
@Slf4j
@Component
public class WebFilter extends ZuulFilter {


    /**
     * 表示在请求前：pre后者请求后 post执行
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多个过滤器执行的顺序，数字越小，表示越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 表示过滤器是否开启，true开启，false不开启
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作，return 任何object类型的值都表示继续执行
     * setsendzuulResponse(false) 就表示不在执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        log.info("经过web网关你过滤器");


        // TODO web前端不需要访问 需要认证的请求，所以可以取消传递token
        //获取request上下文
        RequestContext context = RequestContext.getCurrentContext();
        // 得到request对象
        HttpServletRequest request = context.getRequest();
        // 获取到头信息
        String header = request.getHeader("Authorization");
        // 判断是否有头信息
        if(header != null && !header.equals("")) {
            // 把头信息继续传递到微服务
            context.addZuulRequestHeader("Authorization", header);
        }
        log.info("web网关过滤器放行");
        //放行
        return null;
    }


}
