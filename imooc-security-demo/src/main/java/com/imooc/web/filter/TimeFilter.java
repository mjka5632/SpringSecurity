package com.imooc.web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * 有时候使用第三方的Filter，可能会没有@Component，就需要把Filter添加进web.xml中
 * 因Spring Boot没有web.xml,我们创建WebConfig类来充当
 * 使用@Component所有的都会过滤(无法控制）
 * 在web.xml（WebConfig)中，可以添加过滤的url
 */
//@Component
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("time filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("time filter start");
        long start = new Date().getTime();
        chain.doFilter(request,response);
        System.out.println("time filter 耗时："+(new Date().getTime()-start));
        System.out.println("time filter finish");
    }

    @Override
    public void destroy() {
        System.out.println("time filter destory");
    }
}
