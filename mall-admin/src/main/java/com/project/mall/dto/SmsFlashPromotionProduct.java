package com.project.mall.dto;

import com.project.mall.model.PmsProduct;
import com.project.mall.model.SmsFlashPromotionProductRelation;
import lombok.Getter;
import lombok.Setter;

/**
 * 闂勬劖妞傜拹顓炴櫌閸濅椒淇婇幁顖氱殱鐟?
 * Created by macro on 2018/11/16.
 */
public class SmsFlashPromotionProduct extends SmsFlashPromotionProductRelation{
    @Getter
    @Setter
    private PmsProduct product;
}
