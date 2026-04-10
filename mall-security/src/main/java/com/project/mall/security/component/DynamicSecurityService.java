package com.project.mall.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态权限：由业务模块实现，加载「路径 → 所需权限」映射。
 */
public interface DynamicSecurityService {
    /**
     * 加载资源 ANT 通配符与对应 {@link ConfigAttribute} 的映射。
     */
    Map<String, ConfigAttribute> loadDataSource();
}
