package com.project.mall.dao;

import com.project.mall.dto.PmsProductAttributeCategoryItem;

import java.util.List;

/**
 * 鍟嗗搧灞炴?у垎绫荤鐞嗚嚜瀹氫箟Dao
 * Created by macro on 2018/5/24.
 */
public interface PmsProductAttributeCategoryDao {
    /**
     * 鑾峰彇鍖呭惈灞炴?х殑鍟嗗搧灞炴?у垎绫?
     */
    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
