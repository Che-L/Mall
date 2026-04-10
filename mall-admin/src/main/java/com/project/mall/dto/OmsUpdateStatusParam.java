package com.project.mall.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 绾喛顓婚弨鎯版彛鐠囬攱鐪伴崣鍌涙殶
 * Created by macro on 2018/10/18.
 */
@Getter
@Setter
public class OmsUpdateStatusParam {
    private Long id;
    private Long companyAddressId;
    private BigDecimal returnAmount;
    private String handleNote;
    private String handleMan;
    private String receiveNote;
    private String receiveMan;
    private Integer status;
}
