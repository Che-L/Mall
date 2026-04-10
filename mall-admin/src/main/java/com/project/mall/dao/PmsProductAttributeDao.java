package com.project.mall.dao;

import com.project.mall.dto.ProductAttrInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 鍟嗗搧灞炴?х鐞嗚嚜瀹氫箟Dao
 * Created by macro on 2018/5/23.
 */
public interface PmsProductAttributeDao {
    /**
     * 鑾峰彇鍟嗗搧灞炴?т俊鎭?
     */
    List<ProductAttrInfo> getProductAttrInfo(@Param("id") Long productCategoryId);
}
