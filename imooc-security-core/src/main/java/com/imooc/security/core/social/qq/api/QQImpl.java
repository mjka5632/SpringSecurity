package com.imooc.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 转换成实体的工具类
     */
    ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 获得openId的URL
     */
    public static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    /**
     * 获取社交用户的信息的URL
     */
    public static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    //申请QQ登录，分配给应用的appid
    private String appId;
    //QQ用户对应的ID
    private String openId;

    /**
     * 通过this关键字保存openId
     * @param accessToken
     * @param appId
     */
    public QQImpl(String accessToken, String appId) {
        //TokenStrategy.ACCESS_TOKEN_PARAMETER 作为查询参数携带
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        //替换URL_GET_OPENID中的%s
        String url = String.format(URL_GET_OPENID, accessToken);
        //利用AbstractOAuth2ApiBinding父类
        //方法发送Get请求，得到返回结果
        // callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
        String result = getRestTemplate().getForObject(url, String.class);
        logger.info("获取Callback的信息：" + result);
        //截断获取OpenId
        // callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");


    }

    @Override
    public QQUserInfo getUserInfo() {
        //替换URL中的%s
        String url = String.format(URL_GET_USERINFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);

        logger.info("获取UserInfo的信息" + result);

        QQUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户失败", e);
        }
    }
}
