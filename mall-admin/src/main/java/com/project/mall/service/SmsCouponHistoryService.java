package com.project.mall.service;

import com.project.mall.model.SmsCouponHistory;

import java.util.List;

/**
 * 浼樻儬鍒搁鍙栬褰曠鐞哠ervice
 * Created by macro on 2018/11/6.
 */
public interface SmsCouponHistoryService {
    /**
     * 鍒嗛〉鏌ヨ浼樻儬鍒搁鍙栬褰?
     * @param couponId 浼樻儬鍒竔d
     * @param useStatus 浣跨敤鐘舵??
     * @param orderSn 浣跨敤璁㈠崟鍙风爜
     */
    List<SmsCouponHistory> list(Long couponId, Integer useStatus, String orderSn, Integer pageSize, Integer pageNum);
}
