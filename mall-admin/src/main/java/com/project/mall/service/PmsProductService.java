package com.project.mall.service;

import com.project.mall.dto.PmsProductParam;
import com.project.mall.dto.PmsProductQueryParam;
import com.project.mall.dto.PmsProductResult;
import com.project.mall.model.PmsProduct;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 鍟嗗搧绠＄悊Service
 * Created by macro on 2018/4/26.
 */
public interface PmsProductService {
    /**
     * 鍒涘缓鍟嗗搧
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    int create(PmsProductParam productParam);

    /**
     * 鏍规嵁鍟嗗搧缂栧彿鑾峰彇鏇存柊淇℃伅
     */
    PmsProductResult getUpdateInfo(Long id);

    /**
     * 鏇存柊鍟嗗搧
     */
    @Transactional
    int update(Long id, PmsProductParam productParam);

    /**
     * 鍒嗛〉鏌ヨ鍟嗗搧
     */
    List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum);

    /**
     * 鎵归噺淇敼瀹℃牳鐘舵??
     * @param ids 浜у搧id
     * @param verifyStatus 瀹℃牳鐘舵??
     * @param detail 瀹℃牳璇︽儏
     */
    @Transactional
    int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail);

    /**
     * 鎵归噺淇敼鍟嗗搧涓婃灦鐘舵??
     */
    int updatePublishStatus(List<Long> ids, Integer publishStatus);

    /**
     * 鎵归噺淇敼鍟嗗搧鎺ㄨ崘鐘舵??
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 鎵归噺淇敼鏂板搧鐘舵??
     */
    int updateNewStatus(List<Long> ids, Integer newStatus);

    /**
     * 鎵归噺鍒犻櫎鍟嗗搧
     */
    int updateDeleteStatus(List<Long> ids, Integer deleteStatus);

    /**
     * 鏍规嵁鍟嗗搧鍚嶇О鎴栬?呰揣鍙锋ā绯婃煡璇?
     */
    List<PmsProduct> list(String keyword);
}
