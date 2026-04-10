package com.project.mall.service;

import com.project.mall.dto.PmsProductCategoryParam;
import com.project.mall.dto.PmsProductCategoryWithChildrenItem;
import com.project.mall.model.PmsProductCategory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 鍟嗗搧鍒嗙被绠＄悊Service
 * Created by macro on 2018/4/26.
 */
public interface PmsProductCategoryService {
    /**
     * 鍒涘缓鍟嗗搧鍒嗙被
     */
    @Transactional
    int create(PmsProductCategoryParam pmsProductCategoryParam);

    /**
     * 淇敼鍟嗗搧鍒嗙被
     */
    @Transactional
    int update(Long id, PmsProductCategoryParam pmsProductCategoryParam);

    /**
     * 鍒嗛〉鑾峰彇鍟嗗搧鍒嗙被
     */
    List<PmsProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 鍒犻櫎鍟嗗搧鍒嗙被
     */
    int delete(Long id);

    /**
     * 鏍规嵁ID鑾峰彇鍟嗗搧鍒嗙被
     */
    PmsProductCategory getItem(Long id);

    /**
     * 鎵归噺淇敼瀵艰埅鐘舵??
     */
    int updateNavStatus(List<Long> ids, Integer navStatus);

    /**
     * 鎵归噺淇敼鏄剧ず鐘舵??
     */
    int updateShowStatus(List<Long> ids, Integer showStatus);

    /**
     * 浠ュ眰绾у舰寮忚幏鍙栧晢鍝佸垎绫?
     */
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
