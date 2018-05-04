package com.imooc.security.core.social.qq.connect;

import com.imooc.security.core.social.qq.api.QQ;
import com.imooc.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * QQ服务提供商
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
    /**
     * QQ互联提供的ID
     */
    private String appId;
    /**
     * 获取授权码的URL
     */
    public static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    /**
     * 通过授权码获取Token的URL
     */
    public static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    /**
     * @param appId     QQ互联提供的ID
     * @param appSecret QQ互联提供的密码
     */
    public QQServiceProvider(String appId, String appSecret) {
        super(new QQAUth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
