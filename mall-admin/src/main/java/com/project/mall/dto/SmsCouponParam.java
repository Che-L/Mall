package com.project.mall.dto;

import com.project.mall.model.SmsCoupon;
import com.project.mall.model.SmsCouponProductCategoryRelation;
import com.project.mall.model.SmsCouponProductRelation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 娴兼ɑ鍎崚闀愪繆閹垰鐨濈憗鍜冪礉閸栧懏瀚紒鎴濈暰閸熷棗鎼ч崪灞藉瀻缁?
 * Created by macro on 2018/8/28.
 */
public class SmsCouponParam extends SmsCoupon {
    @Getter
    @Setter
    private List<SmsCouponProductRelation> productRelationList;
    @Getter
    @Setter
    private List<SmsCouponProductCategoryRelation> productCategoryRelationList;
}
