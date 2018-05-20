package com.imooc.security.browser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * session并发
 */
public class ImoocConcurrencySessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {


    /**
     * @param invalidSessionUrl
     */
    public ImoocConcurrencySessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        event.getResponse().setContentType("application/json;charset=utf-8");
        event.getResponse().getWriter().write("并发登录！");
    }
}
