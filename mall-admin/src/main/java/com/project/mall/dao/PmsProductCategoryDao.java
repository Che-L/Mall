package com.project.mall.dao;

import com.project.mall.dto.PmsProductCategoryWithChildrenItem;

import java.util.List;

/**
 * 鍟嗗搧鍒嗙被鑷畾涔塂ao
 * Created by macro on 2018/5/25.
 */
public interface PmsProductCategoryDao {
    /**
     * 鑾峰彇鍟嗗搧鍒嗙被鍙婂叾瀛愬垎绫?
     */
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
