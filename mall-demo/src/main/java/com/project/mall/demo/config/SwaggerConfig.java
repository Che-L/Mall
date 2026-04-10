package com.project.mall.demo.config;

import com.project.mall.common.config.BaseSwaggerConfig;
import com.project.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI（SpringDoc）文档配置
 */
@Configuration
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.project.mall.demo.controller")
                .title("mall-demo 系统")
                .description("Spring Boot 版本中的一些示例接口")
                .contactName("macro")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
