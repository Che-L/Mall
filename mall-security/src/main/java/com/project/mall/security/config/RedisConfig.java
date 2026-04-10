package com.project.mall.security.config;

import com.project.mall.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Redis 配置（继承公共 {@link BaseRedisConfig}）。
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {

}
