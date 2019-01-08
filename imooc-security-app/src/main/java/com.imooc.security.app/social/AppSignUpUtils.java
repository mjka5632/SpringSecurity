package com.imooc.security.app.social;

import com.imooc.security.app.AppSecretException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

/**
 * APP跟第三方工具类
 */
@Component
public class AppSignUpUtils {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    /**
     * 保存第三方用户信息(签约)
     *
     * @param request
     * @param connectionData
     */
    public void saveConnectionData(WebRequest request, ConnectionData connectionData) {
        redisTemplate.opsForValue().set(getKey(request), connectionData, 10, TimeUnit.MINUTES);

    }

    /**
     * 绑定用户信息插入数据(注册)
     *
     * @param request
     * @param userId
     */
    public void doPostSignUp(WebRequest request, String userId) {
        String key = getKey(request);
        if (!redisTemplate.hasKey(key)) {
            throw new AppSecretException("无法找到缓存中的社交账户的信息");
        }
        ConnectionData data = (ConnectionData) redisTemplate.opsForValue().get(key);

        Connection<?> connection = connectionFactoryLocator.getConnectionFactory(data.getProviderId()).createConnection(data);

        usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);
        //删除缓存的数据
        redisTemplate.delete(key);

    }

    /**
     * 命名key
     *
     * @param request
     * @return
     */
    private String getKey(WebRequest request) {

        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new AppSecretException("设备ID不能为空");
        }
        return "imooc:security:social.connect." + deviceId;

    }
}
