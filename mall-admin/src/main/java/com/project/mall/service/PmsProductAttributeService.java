package com.project.mall.service;

import com.project.mall.dto.PmsProductAttributeParam;
import com.project.mall.dto.ProductAttrInfo;
import com.project.mall.model.PmsProductAttribute;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 鍟嗗搧灞炴?х鐞哠ervice
 * Created by macro on 2018/4/26.
 */
public interface PmsProductAttributeService {
    /**
     * 鏍规嵁鍒嗙被鍒嗛〉鑾峰彇鍟嗗搧灞炴??
     * @param cid 鍒嗙被id
     * @param type 0->瑙勬牸锛?->鍙傛暟
     */
    List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum);

    /**
     * 娣诲姞鍟嗗搧灞炴??
     */
    @Transactional
    int create(PmsProductAttributeParam pmsProductAttributeParam);

    /**
     * 淇敼鍟嗗搧灞炴??
     */
    int update(Long id, PmsProductAttributeParam productAttributeParam);

    /**
     * 鑾峰彇鍗曚釜鍟嗗搧灞炴?т俊鎭?
     */
    PmsProductAttribute getItem(Long id);

    /**
     * 鎵归噺鍒犻櫎鍟嗗搧灞炴??
     */
    @Transactional
    int delete(List<Long> ids);

    /**
     * 鑾峰彇鍟嗗搧鍒嗙被瀵瑰簲灞炴?у垪琛?
     */
    List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);
}
