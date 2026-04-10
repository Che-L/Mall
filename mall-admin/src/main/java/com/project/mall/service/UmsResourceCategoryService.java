package com.project.mall.service;

import com.project.mall.model.UmsResourceCategory;

import java.util.List;

/**
 * 鍚庡彴璧勬簮鍒嗙被绠＄悊Service
 * Created by macro on 2020/2/5.
 */
public interface UmsResourceCategoryService {

    /**
     * 鑾峰彇鎵?鏈夎祫婧愬垎绫?
     */
    List<UmsResourceCategory> listAll();

    /**
     * 鍒涘缓璧勬簮鍒嗙被
     */
    int create(UmsResourceCategory umsResourceCategory);

    /**
     * 淇敼璧勬簮鍒嗙被
     */
    int update(Long id, UmsResourceCategory umsResourceCategory);

    /**
     * 鍒犻櫎璧勬簮鍒嗙被
     */
    int delete(Long id);
}
