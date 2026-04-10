package com.project.mall.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 鐠併垹宕熼柅?鐠愌呮暤鐠囬攱鐓＄拠銏犲棘閺?
 * Created by macro on 2018/10/18.
 */
@Getter
@Setter
public class OmsReturnApplyQueryParam {
    private Long id;
    private String receiverKeyword;
    private Integer status;
    private String createTime;
    private String handleMan;
    private String handleTime;
}
