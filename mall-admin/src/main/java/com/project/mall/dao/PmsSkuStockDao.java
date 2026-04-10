package com.project.mall.dao;

import com.project.mall.model.PmsSkuStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 鍟嗗搧SKU绠＄悊鑷畾涔塂ao
 * Created by macro on 2018/4/26.
 */
public interface PmsSkuStockDao {
    /**
     * 鎵归噺鎻掑叆鎿嶄綔
     */
    int insertList(@Param("list")List<PmsSkuStock> skuStockList);

    /**
     * 鎵归噺鎻掑叆鎴栨浛鎹㈡搷浣?
     */
    int replaceList(@Param("list")List<PmsSkuStock> skuStockList);
}
