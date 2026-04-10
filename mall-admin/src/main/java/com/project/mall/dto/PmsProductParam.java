package com.project.mall.dto;

import com.project.mall.model.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 閸掓稑缂撻崪灞兼叏閺€鐟版櫌閸濅胶娈戠拠閿嬬湴閸欏倹鏆?
 * Created by macro on 2018/4/26.
 */
@Data
@EqualsAndHashCode
public class PmsProductParam extends PmsProduct{
    private List<PmsProductLadder> productLadderList;
    private List<PmsProductFullReduction> productFullReductionList;
    private List<PmsMemberPrice> memberPriceList;
    private List<PmsSkuStock> skuStockList;
    private List<PmsProductAttributeValue> productAttributeValueList;
    private List<CmsSubjectProductRelation> subjectProductRelationList;
    private List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList;
}
