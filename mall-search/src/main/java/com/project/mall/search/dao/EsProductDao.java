package com.project.mall.search.dao;

import com.project.mall.search.domain.EsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 从 MySQL 组装 ES 文档用的自定义 Dao。
 */
public interface EsProductDao {

    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
