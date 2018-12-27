package com.imooc.security.app.social.openid;

import com.imooc.security.core.properties.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 【第三步】Filter
 * 验证流程see{@link OpenIdAuthenticationSecurityConfig}
 */
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private String openIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_OPENID;
    private String providerIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_PROVIDERID;
    private boolean postOnly = true;

    protected OpenIdAuthenticationFilter() {
        //这里匹配访问地址
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID, "POST"));
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported" + request.getMethod());
        }

        String openId = obtainOpenId(request);
        String providerId = obtainProviderId(request);
        if (openId == null) {
            openId = "";
        }
        if (providerId == null) {
            providerId = "";
        }
        openId.trim();
        providerId.trim();
        OpenIdAuthenticationToken authRequest = new OpenIdAuthenticationToken(openId, providerId);
        setDetails(request, authRequest);
        //这里去找支持Token的Provider
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 获取openId
     *
     * @param request
     * @return
     */
    protected String obtainOpenId(HttpServletRequest request) {
        return request.getParameter(openIdParameter);
    }

    /**
     * 获取供应商的ID
     *
     * @param request
     * @return
     */
    protected String obtainProviderId(HttpServletRequest request) {
        return request.getParameter(providerIdParameter);
    }

    protected void setDetails(HttpServletRequest request, OpenIdAuthenticationToken token) {
        token.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setOpenIdParameter(String openIdParameter) {
        Assert.hasText(openIdParameter, "OpenId parameter must not be empty or null");
        this.openIdParameter = openIdParameter;
    }

    public String getOpenIdParameter() {
        return openIdParameter;
    }

    public String getProviderIdParameter() {
        return providerIdParameter;
    }

    public void setProviderIdParameter(String providerIdParameter) {
        this.providerIdParameter = providerIdParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
