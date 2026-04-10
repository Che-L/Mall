package com.project.mall.service;

import com.project.mall.model.SmsHomeBrand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 棣栭〉鍝佺墝绠＄悊Service
 * Created by macro on 2018/11/6.
 */
public interface SmsHomeBrandService {
    /**
     * 娣诲姞棣栭〉鍝佺墝鎺ㄨ崘
     */
    @Transactional
    int create(List<SmsHomeBrand> homeBrandList);

    /**
     * 淇敼鍝佺墝鎺ㄨ崘鎺掑簭
     */
    int updateSort(Long id, Integer sort);

    /**
     * 鎵归噺鍒犻櫎鍝佺墝鎺ㄨ崘
     */
    int delete(List<Long> ids);

    /**
     * 鎵归噺鏇存柊鎺ㄨ崘鐘舵??
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 鍒嗛〉鏌ヨ鍝佺墝鎺ㄨ崘
     */
    List<SmsHomeBrand> list(String brandName, Integer recommendStatus, Integer pageSize, Integer pageNum);
}
