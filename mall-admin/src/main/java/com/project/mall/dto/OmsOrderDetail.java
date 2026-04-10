package com.project.mall.dto;

import com.project.mall.model.OmsOrder;
import com.project.mall.model.OmsOrderItem;
import com.project.mall.model.OmsOrderOperateHistory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 璁㈠崟璇︽儏淇℃伅
 * Created by macro on 2018/10/11.
 */
public class OmsOrderDetail extends OmsOrder {
    @Getter
    @Setter
    private List<OmsOrderItem> orderItemList;
    @Getter
    @Setter
    private List<OmsOrderOperateHistory> historyList;
}
