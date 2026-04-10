package com.project.mall.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 鐠併垹宕熸穱顔芥暭閺€鎯版彛娴滆桨淇婇幁顖氬棘閺?
 * Created by macro on 2018/10/29.
 */
@Getter
@Setter
public class OmsReceiverInfoParam {
    private Long orderId;
    private String receiverName;
    private String receiverPhone;
    private String receiverPostCode;
    private String receiverDetailAddress;
    private String receiverProvince;
    private String receiverCity;
    private String receiverRegion;
    private Integer status;
}
