package com.project.mall.common.config;

import com.project.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Bean;
import org.springdoc.core.models.GroupedOpenApi;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * OpenAPI基础配置
 * Created by macro on 2020/7/16.
 */
public abstract class BaseSwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        SwaggerProperties swaggerProperties = swaggerProperties();
        OpenAPI openAPI = new OpenAPI()
                .info(apiInfo(swaggerProperties));
        if (swaggerProperties.isEnableSecurity()) {
            String securitySchemeName = "Authorization";
            openAPI.components(new Components().addSecuritySchemes(securitySchemeName,
                    new SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")));
            openAPI.addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
        }
        return openAPI;
    }

    @Bean
    public GroupedOpenApi publicApi() {
        SwaggerProperties swaggerProperties = swaggerProperties();
        return GroupedOpenApi.builder()
                .group("default")
                .packagesToScan(swaggerProperties.getApiBasePackage())
                .build();
    }

    private Info apiInfo(SwaggerProperties swaggerProperties) {
        return new Info()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .contact(new Contact()
                        .name(swaggerProperties.getContactName())
                        .url(swaggerProperties.getContactUrl())
                        .email(swaggerProperties.getContactEmail()))
                .version(swaggerProperties.getVersion());
    }

    /**
     * 自定义Swagger配置
     */
    public abstract SwaggerProperties swaggerProperties();
}
