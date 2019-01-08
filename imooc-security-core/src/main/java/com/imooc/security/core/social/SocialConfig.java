package com.imooc.security.core.social;

import com.imooc.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 社交登录配置类
 *
 * @EnableSocial注解 启动Spring social相关特性
 */
@Configuration
@EnableSocial
@Order(10)
public class SocialConfig extends SocialConfigurerAdapter {
    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 自动注册新用户(可选择)
     */
    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    /**
     * 去查找ConnectionFactory
     * [TextEncryptor:用来加解密的工具  Encryptors.noOpText()数据不加密]
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        //如果表名加了前缀用这个修改
//        repository.setTablePrefix("imooc_");
        //判断是否自动注册用户
        if (connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * 自定义社交配置
     *
     * @return
     */
    @Bean
    public SpringSocialConfigurer imoocSocialSecurityConfig() {
        //修改默认的FilterProcessesUrl
        ImoocSpringSocialConfigurer socialConfigurer = new ImoocSpringSocialConfigurer(securityProperties.getSocial().getFilterProcessesUrl());
        //指定签约页面地址
        socialConfigurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        socialConfigurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return socialConfigurer;
    }

    /**
     * 注册工具类
     *
     * @param locator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator locator) {
        return new ProviderSignInUtils(locator, getUsersConnectionRepository(locator));
    }


}
