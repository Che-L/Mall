package com.project.mall.service;

import com.project.mall.dto.PmsProductAttributeCategoryItem;
import com.project.mall.model.PmsProductAttributeCategory;

import java.util.List;

/**
 * 鍟嗗搧灞炴?у垎绫荤鐞哠ervice
 * Created by macro on 2018/4/26.
 */
public interface PmsProductAttributeCategoryService {
    /**
     * 鍒涘缓灞炴?у垎绫?
     */
    int create(String name);

    /**
     * 淇敼灞炴?у垎绫?
     */
    int update(Long id, String name);

    /**
     * 鍒犻櫎灞炴?у垎绫?
     */
    int delete(Long id);

    /**
     * 鑾峰彇灞炴?у垎绫昏鎯?
     */
    PmsProductAttributeCategory getItem(Long id);

    /**
     * 鍒嗛〉鏌ヨ灞炴?у垎绫?
     */
    List<PmsProductAttributeCategory> getList(Integer pageSize, Integer pageNum);

    /**
     * 鑾峰彇鍖呭惈灞炴?х殑灞炴?у垎绫?
     */
    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
