package com.project.mall.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 鐠併垹宕熼弻銉嚄閸欏倹鏆?
 * Created by macro on 2018/10/11.
 */
@Getter
@Setter
public class OmsOrderQueryParam {
    private String orderSn;
    private String receiverKeyword;
    private Integer status;
    private Integer orderType;
    private Integer sourceType;
    private String createTime;
}
