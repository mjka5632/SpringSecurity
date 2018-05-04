package com.imooc.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 配置请求地址前半部分 默认为 /auth
 * <p>
 * SpringSocialConfigurer这个类中添加过滤器时
 * 最后调用了postProcess，所以重写这个方法，设置自己的URL前缀
 */
public class ImoocSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public ImoocSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    //
    @Override
    protected <T> T postProcess(T object) {

        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        //设置URL前缀
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) filter;
    }
}
