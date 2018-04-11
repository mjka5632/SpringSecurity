package com.imooc.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 短信验证码 配置
 */
@Configuration
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {

    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
    /**
     * 指定UserDetailsService实现类
     */
    @Autowired
    @Qualifier("mobileDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //-----------过滤器
        SmsCodeAuthenticationFilter filter = new SmsCodeAuthenticationFilter();
        //设置AuthenticationManager来做认证
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //成功失败处理器
        filter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
        filter.setAuthenticationSuccessHandler(imoocAuthenticationSuccessHandler);


        //------------provider
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);


        http.authenticationProvider(provider)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);

    }
}
