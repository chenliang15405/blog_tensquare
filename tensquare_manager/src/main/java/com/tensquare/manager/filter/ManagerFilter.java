package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 网关过滤器，因为请求经过网关之后，没有将头信息转发给微服务，所以需要在过滤器中
 *  进行处理，然后重新传递头信息 否则token无法验证
 *
 * @auther alan.chen
 * @time 2019/6/15 5:47 PM
 */
@Slf4j
@Component
public class ManagerFilter extends ZuulFilter {
    /**
     * 表示在请求前：pre后者请求后 post执行
     *
     * @return
     */
    @Override
    public String filterType() { // 过滤器类型
        return "pre"; // 前置过滤器
    }

    /**
     * 多个过滤器执行的顺序，数字越小，表示越先执行
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;   // 优先级
    }

    /**
     * 表示过滤器是否开启，true开启，false不开启
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;    //过滤器开关
    }


    /**
     * 网关会将 header中的信息过滤掉，所以，需要将header中带的token重新放入到转发之后的请求中
     *
     *  过滤器内执行的操作，return 任何object类型的值都表示继续执行
     *  context.setSendZuulResponse(false); 就表示不在执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        log.info("经过Manager网关过滤器");
        //得到request上下文
        RequestContext context = RequestContext.getCurrentContext();
        //得到request域
        HttpServletRequest request = context.getRequest();

        try {
            //网关内部还有一个分发的请求，还没有向微服务跳转的时候，所以这个请求直接可以放行
            // 这个请求是， 如果header中包含Authorization, 则会在发送请求之前发送一次 options 请求验证。
            if(request.getMethod().equals("OPTIONS")){
                //放行
                return null;
            }

            //如果是登录请求，则直接放行
            if(request.getRequestURI().indexOf("login")>0){
                System.out.println("admin登录页面url : " + request.getRequestURL().toString());
                return null;
            }

            //得到头信息,
            String header = request.getHeader("Authorization");

            if(header == null || header.trim().equals("") ) {
                context.setSendZuulResponse(false);
                context.setResponseStatusCode(403);
                context.setResponseBody("权限不足");
                context.getResponse().setContentType("text/html;charset=utf-8");
                log.info("进入管理端权限不足，Manager filter 拦截");
                return null;
            }

            // 后面使用的spring security 所以可以直接将Authorization 重新设置进去就可以，后面会解析，在过滤器中就不用解析
            context.addZuulRequestHeader("Authorization", header);

            log.info("Manager filter 放行");
        } catch (Exception e) {
            log.error("Manager filter 转发出现异常 ", e);
            context.setSendZuulResponse(false);
        }

        return null;
    }


}
