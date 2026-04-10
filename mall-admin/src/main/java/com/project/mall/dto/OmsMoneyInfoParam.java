package com.project.mall.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 娣囶喗鏁肩拋銏犲礋鐠愬湱鏁ゆ穱鈩冧紖閸欏倹鏆?
 * Created by macro on 2018/10/29.
 */
@Getter
@Setter
public class OmsMoneyInfoParam {
    private Long orderId;
    private BigDecimal freightAmount;
    private BigDecimal discountAmount;
    private Integer status;
}
