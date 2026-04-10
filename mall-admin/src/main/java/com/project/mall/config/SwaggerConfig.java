package com.project.mall.config;

import com.project.mall.common.config.BaseSwaggerConfig;
import com.project.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger相关配置
 * Created by macro on 2018/4/26.
 */
@Configuration
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.project.mall.controller")
                .title("mall后台系统")
                .description("mall后台相关接口文档")
                .contactName("macro")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }

}
