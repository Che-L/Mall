package com.project.mall.search.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis：扫描 MBG 生成 Mapper 与搜索模块自定义 Dao。
 */
@Configuration
@MapperScan({"com.project.mall.mapper", "com.project.mall.search.dao"})
public class MyBatisConfig {
}
