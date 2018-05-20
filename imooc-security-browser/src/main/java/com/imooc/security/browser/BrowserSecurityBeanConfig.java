package com.imooc.security.browser;


import com.imooc.security.browser.logout.ImoocLogoutSuccessHandler;
import com.imooc.security.browser.session.ImoocConcurrencySessionStrategy;
import com.imooc.security.browser.session.ImoocInvalidSessionStrategy;
import com.imooc.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
@Configuration
public class BrowserSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * session过期
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return  new ImoocInvalidSessionStrategy(securityProperties.getBrowser().getSession().getInvalidSessionUrl());
    }

    /**
     * session并发
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return  new ImoocConcurrencySessionStrategy(securityProperties.getBrowser().getSession().getInvalidSessionUrl());
    }

    /**
     * 退出
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler(){
        return  new ImoocLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
    }

}
