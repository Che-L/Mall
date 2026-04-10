package com.project.mall.service;

import com.project.mall.dto.OmsOrderReturnApplyResult;
import com.project.mall.dto.OmsReturnApplyQueryParam;
import com.project.mall.dto.OmsUpdateStatusParam;
import com.project.mall.model.OmsOrderReturnApply;

import java.util.List;

/**
 * 閫?璐х敵璇风鐞哠ervice
 * Created by macro on 2018/10/18.
 */
public interface OmsOrderReturnApplyService {
    /**
     * 鍒嗛〉鏌ヨ鐢宠
     */
    List<OmsOrderReturnApply> list(OmsReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum);

    /**
     * 鎵归噺鍒犻櫎鐢宠
     */
    int delete(List<Long> ids);

    /**
     * 淇敼鎸囧畾鐢宠鐘舵??
     */
    int updateStatus(Long id, OmsUpdateStatusParam statusParam);

    /**
     * 鑾峰彇鎸囧畾鐢宠璇︽儏
     */
    OmsOrderReturnApplyResult getItem(Long id);
}
