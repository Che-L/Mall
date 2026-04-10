package com.project.mall.service;

import com.project.mall.dto.SmsFlashPromotionSessionDetail;
import com.project.mall.model.SmsFlashPromotionSession;

import java.util.List;

/**
 * 闄愭椂璐満娆＄鐞哠ervice
 * Created by macro on 2018/11/16.
 */
public interface SmsFlashPromotionSessionService {
    /**
     * 娣诲姞鍦烘
     */
    int create(SmsFlashPromotionSession promotionSession);

    /**
     * 淇敼鍦烘
     */
    int update(Long id, SmsFlashPromotionSession promotionSession);

    /**
     * 淇敼鍦烘鍚敤鐘舵??
     */
    int updateStatus(Long id, Integer status);

    /**
     * 鍒犻櫎鍦烘
     */
    int delete(Long id);

    /**
     * 鑾峰彇璇︽儏
     */
    SmsFlashPromotionSession getItem(Long id);

    /**
     * 鏍规嵁鍚敤鐘舵?佽幏鍙栧満娆″垪琛?
     */
    List<SmsFlashPromotionSession> list();

    /**
     * 鑾峰彇鍏ㄩ儴鍙?夊満娆″強鍏舵暟閲?
     */
    List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId);
}
