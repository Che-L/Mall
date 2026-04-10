package com.project.mall.security.annotation;

import java.lang.annotation.*;

/**
 * 标在缓存方法上：Redis 等异常时向调用方抛出，而不是被切面吞掉。
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
