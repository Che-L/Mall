package com.project.mall.security.config;

import com.project.mall.security.component.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Spring Security 相关配置，仅用于装配 {@link SecurityFilterChain}。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired(required = false)
    private DynamicSecurityService dynamicSecurityService;
    @Autowired(required = false)
    private DynamicSecurityFilter dynamicSecurityFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(restfulAccessDeniedHandler)
                        .authenticationEntryPoint(restAuthenticationEntryPoint))
                .authorizeHttpRequests(authorize -> {
                    // 不需要保护的资源路径允许访问
                    for (String url : ignoreUrlsConfig.getUrls()) {
                        authorize.requestMatchers(url).permitAll();
                    }
                    // 允许跨域请求的 OPTIONS
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    // 其余请求需要认证
                    authorize.anyRequest().authenticated();
                })
                // JWT 过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 配置了动态权限时追加动态权限校验过滤器
        if (dynamicSecurityService != null) {
            httpSecurity.addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
        }
        return httpSecurity.build();
    }

}
