package com.project.mall.service;

import com.project.mall.model.OmsOrderReturnReason;

import java.util.List;

/**
 * 閫?璐у師鍥犵鐞哠ervice
 * Created by macro on 2018/10/17.
 */
public interface OmsOrderReturnReasonService {
    /**
     * 娣诲姞閫?璐у師鍥?
     */
    int create(OmsOrderReturnReason returnReason);

    /**
     * 淇敼閫?璐у師鍥?
     */
    int update(Long id, OmsOrderReturnReason returnReason);

    /**
     * 鎵归噺鍒犻櫎閫?璐у師鍥?
     */
    int delete(List<Long> ids);

    /**
     * 鍒嗛〉鑾峰彇閫?璐у師鍥?
     */
    List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum);

    /**
     * 鎵归噺淇敼閫?璐у師鍥犵姸鎬?
     */
    int updateStatus(List<Long> ids, Integer status);

    /**
     * 鑾峰彇鍗曚釜閫?璐у師鍥犺鎯呬俊鎭?
     */
    OmsOrderReturnReason getItem(Long id);
}
