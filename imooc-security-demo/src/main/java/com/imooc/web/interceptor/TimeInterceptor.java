package com.imooc.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
@Component
public class TimeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handle) throws Exception {
        System.out.println("preHandle");
        System.out.println(((HandlerMethod)handle).getBean().getClass().getName());
        System.out.println(((HandlerMethod)handle).getMethod().getName());

        httpServletRequest.setAttribute("startTime",new Date().getTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handle, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        Long startTime = (Long) httpServletRequest.getAttribute("startTime");

        System.out.println("time interceptor 耗时："+(new Date().getTime()-startTime));

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handle, Exception e) throws Exception {
        System.out.println("afterCompletion");

        Long startTime = (Long) httpServletRequest.getAttribute("startTime");

        System.out.println("time interceptor 耗时："+(new Date().getTime()-startTime));
        System.out.println("ex"+e);

    }
}
