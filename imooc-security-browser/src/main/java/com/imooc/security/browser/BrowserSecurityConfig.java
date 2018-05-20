package com.imooc.security.browser;

import com.imooc.security.browser.session.ImoocConcurrencySessionStrategy;
import com.imooc.security.core.authentication.AbstractChannelSecurityConfig;
import com.imooc.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;


@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {
    @Autowired
    private SecurityProperties securityProperties;
    //IDE错误，此处忽略
    @Autowired
    private DataSource dataSource;
    /**
     * 指定UserDetailsService接口的实现类
     */
    @Autowired
    @Qualifier("myUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer imoocSocialSecurityConfig;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;



    /**
     * 配置表单登录UserDetailsService的实现并设置加密方式
     * 【此处不设置将会走默认实现方式，导致我们出现错误
     * 详情请看 实现UserDetailsService达成用户名登录和短信登录的问答】
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        applyPassowrdAuthenticationConfig(http);
        //导入配置
        http.apply(validateCodeSecurityConfig)
                .and()
            //--------------身份认证
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
            .apply(imoocSocialSecurityConfig)
                .and()
            //------------以下浏览器特有配置--------------
            // 记住我功能
            .rememberMe()
                //设置数据库Token
                .tokenRepository(persistentTokenRepository())
                //设置时效
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                //拿到User信息
                .userDetailsService(userDetailsService)
                .and()
            .sessionManagement()
                .invalidSessionUrl(securityProperties.getBrowser().getSession().getInvalidSessionUrl())
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(new ImoocConcurrencySessionStrategy(securityProperties.getBrowser().getSession().getInvalidSessionUrl()))
                .and()
                .and()
            .logout()
                .logoutUrl("/signOut")
                .logoutSuccessHandler(logoutSuccessHandler)
                //配置logoutSuccessHandler这个就不起作用了
//                .logoutSuccessUrl("/imooc-logout.html")
                .deleteCookies("JSESSIONID")
                //basic方式
//          http.httpBasic()
                .and()
            //--------对请求做授权(下面都是对授权的配置)
            .authorizeRequests()
                //url无需认证
                .antMatchers(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        securityProperties.getBrowser().getSignUpUrl(),
                        securityProperties.getBrowser().getLoginPage(),
                        securityProperties.getBrowser().getSignOutUrl(),
                        SecurityConstants.DEFAULT_SESSION_INVALID,
                        SecurityConstants.DEFAULT_USER_INFO,
                        SecurityConstants.DEFAULT_USER_REGIST,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*").permitAll()
                //任何请求
            .anyRequest()

                //都需要做身份认证
                .authenticated()
                .and()
                //跨站请求防护
                .csrf().disable()

        ;
    }

    /**
     * 记住我功能
     * Token存取器配置
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {

        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //启动时创建表
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    /**
     * 密码加密
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //BCryptPasswordEncoder实现了 PasswordEncoder 接口
        //如果有自己的加密密码方式，返回自己的实现PasswordEncoder的实现类
        return new BCryptPasswordEncoder();
    }
}
