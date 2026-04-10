package com.project.mall.portal.domain;

import com.project.mall.model.OmsOrder;
import com.project.mall.model.OmsOrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 包含商品信息的订单详情
 * Created by macro on 2018/9/4.
 */
@Getter
@Setter
public class OmsOrderDetail extends OmsOrder {
    @Schema(description = "订单商品列表")
    private List<OmsOrderItem> orderItemList;
}
