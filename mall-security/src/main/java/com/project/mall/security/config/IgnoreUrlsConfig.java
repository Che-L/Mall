package com.project.mall.security.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Security 白名单资源路径配置。
 * 绑定前缀由 {@link CommonSecurityConfig#ignoreUrlsConfig()} 上的 @ConfigurationProperties 指定。
 */
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
