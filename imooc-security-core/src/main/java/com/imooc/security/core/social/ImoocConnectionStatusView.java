package com.imooc.security.core.social;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 绑定信息展示
 * 通过UserId进行查找到是谁的
 * 所以MyUserDetailsService必须使用这个类 SocialUserDetails进行接里面有getUserId方法
 */
@Component("connect/status")
public class ImoocConnectionStatusView extends AbstractView {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        //接收ConnectController里存放Model中的Connections
        Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) map.get("connectionMap");
        //用来保存绑定信息
        Map<String, Boolean> result = new HashMap<>();
        for (String key : connections.keySet()) {
            //如果为empty就是false 未绑定
            result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
        }
        //设置返回格式
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        //把result转换成字符串写进去
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));

    }
}
