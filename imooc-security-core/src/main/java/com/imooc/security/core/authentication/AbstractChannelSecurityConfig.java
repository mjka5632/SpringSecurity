package com.imooc.security.core.authentication;

import com.imooc.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
/**
 * 密码登录 配置
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationFailureHandler;

    /**
     * 密码登录相关配置
     * @param http
     * @throws Exception
     */
    protected void applyPassowrdAuthenticationConfig(HttpSecurity http) throws Exception {

        http.formLogin()
                //登录页面跳转
                //.loginPage("/imooc-Sign.html")
                //跳转
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                //登录提交的地址
                //实际在设置UsernamePasswordAuthenticationFilter这个过滤器的处理路径DEFAULT_LOGIN_PROCESSING_URL_FORM
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                //处理成功返回
                .successHandler(imoocAuthenticationSuccessHandler)
                //处理失败返回
                .failureHandler(imoocAuthenticationFailureHandler);
    }

}
