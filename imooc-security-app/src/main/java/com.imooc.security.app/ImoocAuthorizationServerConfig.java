package com.imooc.security.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @EnableAuthorizationServer 当做认证服务器
 */
@Configuration
@EnableAuthorizationServer
public class ImoocAuthorizationServerConfig {
}
