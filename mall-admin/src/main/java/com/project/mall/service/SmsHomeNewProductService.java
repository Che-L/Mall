package com.project.mall.service;

import com.project.mall.model.SmsHomeNewProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 棣栭〉鏂板搧鎺ㄨ崘绠＄悊Service
 * Created by macro on 2018/11/6.
 */
public interface SmsHomeNewProductService {
    /**
     * 娣诲姞棣栭〉鎺ㄨ崘
     */
    @Transactional
    int create(List<SmsHomeNewProduct> homeNewProductList);

    /**
     * 淇敼鎺ㄨ崘鎺掑簭
     */
    int updateSort(Long id, Integer sort);

    /**
     * 鎵归噺鍒犻櫎鎺ㄨ崘
     */
    int delete(List<Long> ids);

    /**
     * 鎵归噺鏇存柊鎺ㄨ崘鐘舵??
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 鍒嗛〉鏌ヨ鎺ㄨ崘
     */
    List<SmsHomeNewProduct> list(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum);
}
