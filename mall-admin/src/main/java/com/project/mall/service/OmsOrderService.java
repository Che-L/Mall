package com.project.mall.service;

import com.project.mall.dto.*;
import com.project.mall.model.OmsOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 璁㈠崟绠＄悊Service
 * Created by macro on 2018/10/11.
 */
public interface OmsOrderService {
    /**
     * 璁㈠崟鏌ヨ
     */
    List<OmsOrder> list(OmsOrderQueryParam queryParam, Integer pageSize, Integer pageNum);

    /**
     * 鎵归噺鍙戣揣
     */
    @Transactional
    int delivery(List<OmsOrderDeliveryParam> deliveryParamList);

    /**
     * 鎵归噺鍏抽棴璁㈠崟
     */
    @Transactional
    int close(List<Long> ids, String note);

    /**
     * 鎵归噺鍒犻櫎璁㈠崟
     */
    int delete(List<Long> ids);

    /**
     * 鑾峰彇鎸囧畾璁㈠崟璇︽儏
     */
    OmsOrderDetail detail(Long id);

    /**
     * 淇敼璁㈠崟鏀惰揣浜轰俊鎭?
     */
    @Transactional
    int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam);

    /**
     * 淇敼璁㈠崟璐圭敤淇℃伅
     */
    @Transactional
    int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam);

    /**
     * 淇敼璁㈠崟澶囨敞
     */
    @Transactional
    int updateNote(Long id, String note, Integer status);
}
