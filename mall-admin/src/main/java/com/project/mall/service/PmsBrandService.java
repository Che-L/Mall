package com.project.mall.service;

import com.project.mall.dto.PmsBrandParam;
import com.project.mall.model.PmsBrand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 鍟嗗搧鍝佺墝绠＄悊Service
 * Created by macro on 2018/4/26.
 */
public interface PmsBrandService {
    /**
     * 鑾峰彇鎵?鏈夊搧鐗?
     */
    List<PmsBrand> listAllBrand();

    /**
     * 鍒涘缓鍝佺墝
     */
    int createBrand(PmsBrandParam pmsBrandParam);

    /**
     * 淇敼鍝佺墝
     */
    @Transactional
    int updateBrand(Long id, PmsBrandParam pmsBrandParam);

    /**
     * 鍒犻櫎鍝佺墝
     */
    int deleteBrand(Long id);

    /**
     * 鎵归噺鍒犻櫎鍝佺墝
     */
    int deleteBrand(List<Long> ids);

    /**
     * 鍒嗛〉鏌ヨ鍝佺墝
     */
    List<PmsBrand> listBrand(String keyword, Integer showStatus, int pageNum, int pageSize);

    /**
     * 鑾峰彇鍝佺墝璇︽儏
     */
    PmsBrand getBrand(Long id);

    /**
     * 淇敼鏄剧ず鐘舵??
     */
    int updateShowStatus(List<Long> ids, Integer showStatus);

    /**
     * 淇敼鍘傚鍒堕?犲晢鐘舵??
     */
    int updateFactoryStatus(List<Long> ids, Integer factoryStatus);
}
