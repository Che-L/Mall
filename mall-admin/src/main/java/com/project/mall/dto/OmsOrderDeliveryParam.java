package com.project.mall.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 璁㈠崟鍙戣揣鍙傛暟
 * Created by macro on 2018/10/12.
 */
@Getter
@Setter
public class OmsOrderDeliveryParam {
    private Long orderId;
    private String deliveryCompany;
    private String deliverySn;
}
