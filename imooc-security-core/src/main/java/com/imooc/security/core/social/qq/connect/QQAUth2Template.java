package com.imooc.security.core.social.qq.connect;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * 重写OAuth2Template中的
 * 方法以适应文档需求
 */
public class QQAUth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public QQAUth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        // clientId clientSecret需要UseParametersForClientAuthentication为true才会传输
        setUseParametersForClientAuthentication(true);
    }

    /**
     * OAuth2Template默认实现的是json的形式
     * 所以需重写方法
     *
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String response = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        logger.info("获取accessToken的响应" + response);
        //access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14。
        String[] items = StringUtils.splitPreserveAllTokens(response, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        long expires = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");

        return new AccessGrant(accessToken, null, refreshToken, expires);
    }

    /**
     * 补充Content Type
     * 重写createRestTemplate
     *
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {
        //调用父类的createRestTemplate
        RestTemplate template = super.createRestTemplate();
      /*1、text/html的意思是将文件的content-type设置为text/html的形式，浏览器在获取到这种文件时会自动调用html的解析器对文件进行相应的处理。
        2、text/plain的意思是将文件设置为纯文本的形式，浏览器在获取到这种文件时并不会对其进行处理。*/
        //父类基础上增加可以处理Content type为text/plain的Convert
        template.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("utf-8")));
        return template;
    }
}
