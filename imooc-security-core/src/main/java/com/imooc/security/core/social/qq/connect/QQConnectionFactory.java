package com.imooc.security.core.social.qq.connect;

import com.imooc.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * ConnectionFactory
 * 需要ServiceProvider和ApiAdapter构成
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {


    /**
     * QQConnectionFactory构造方法
     * @param providerId
     * @param appId
     * @param appSecret
     */
    public QQConnectionFactory(String providerId, String appId,String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }
}
