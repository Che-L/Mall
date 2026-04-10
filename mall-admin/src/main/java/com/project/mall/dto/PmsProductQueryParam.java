package com.project.mall.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 閸熷棗鎼ч弻銉嚄閸欏倹鏆?
 * Created by macro on 2018/4/27.
 */
@Data
@EqualsAndHashCode
public class PmsProductQueryParam {
    private Integer publishStatus;
    private Integer verifyStatus;
    private String keyword;
    private String productSn;
    private Long productCategoryId;
    private Long brandId;
}
