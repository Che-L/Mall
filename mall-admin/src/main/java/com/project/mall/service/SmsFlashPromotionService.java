package com.project.mall.service;

import com.project.mall.model.SmsFlashPromotion;

import java.util.List;

/**
 * 闄愭椂璐椿鍔ㄧ鐞哠ervice
 * Created by macro on 2018/11/16.
 */
public interface SmsFlashPromotionService {
    /**
     * 娣诲姞娲诲姩
     */
    int create(SmsFlashPromotion flashPromotion);

    /**
     * 淇敼鎸囧畾娲诲姩
     */
    int update(Long id, SmsFlashPromotion flashPromotion);

    /**
     * 鍒犻櫎鍗曚釜娲诲姩
     */
    int delete(Long id);

    /**
     * 淇敼涓婁笅绾跨姸鎬?
     */
    int updateStatus(Long id, Integer status);

    /**
     * 鑾峰彇娲诲姩璇︽儏
     */
    SmsFlashPromotion getItem(Long id);

    /**
     * 鍒嗛〉鏌ヨ娲诲姩
     */
    List<SmsFlashPromotion> list(String keyword, Integer pageSize, Integer pageNum);
}
