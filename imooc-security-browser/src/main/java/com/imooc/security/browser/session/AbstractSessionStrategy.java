package com.imooc.security.browser.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.security.browser.domain.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *Session策略
 */
public class AbstractSessionStrategy {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 跳转的URL
     */
    private String destinationUrl;
    /**
     * 重定向策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    /**
     * 跳转前是否创建新的Session
     */
    private boolean createNewSession = true;
    /**
     * 对象转换工具类
     */
    private ObjectMapper objectMappermapper = new ObjectMapper();

    /**
     * Session失效页
     * @param invalidSessionUrl
     */
    public AbstractSessionStrategy(String invalidSessionUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
    }

    /**
     *Session失效跳转【html or json】
     * @param request
     * @param response
     * @throws IOException
     */
    protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (createNewSession) {
            request.getSession();
        }
        //获得URI地址
        String sourceUrl = request.getRequestURI();
        String targetUrl;
        //endsWithIgnoreCase大小写不敏感
        if (StringUtils.endsWithIgnoreCase(sourceUrl, ".html")) {
            targetUrl = destinationUrl + ".html";
            logger.info("session失效，跳转到" + targetUrl);
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            Object result = buildResponseContent(request);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMappermapper.writeValueAsString(result));
        }
    }

    /**
     * 包装返回值
     * @param request
     * @return
     */
    protected Object buildResponseContent(HttpServletRequest request) {
        String message = "session已失效";
        if (isConcurrency()) {
            message = message + ",有可能是并发登录导致的";
        }
        return new SimpleResponse(message);
    }

    protected boolean isConcurrency() {
        return false;
    }

    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }
}
