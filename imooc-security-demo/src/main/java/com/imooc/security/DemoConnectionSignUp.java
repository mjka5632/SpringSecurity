package com.imooc.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * 自动注册新用户
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp {


    @Override
    public String execute(Connection<?> connection) {
        //根据社交用户信息创建用户唯一标示
        //这里会把数据库的社交返回返回得到的providerUserId设置到UserID中
        return connection.getKey().getProviderUserId();

    }
}
