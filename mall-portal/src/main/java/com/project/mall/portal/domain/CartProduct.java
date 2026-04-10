package com.project.mall.portal.domain;

import com.project.mall.model.PmsProduct;
import com.project.mall.model.PmsProductAttribute;
import com.project.mall.model.PmsSkuStock;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 购物车中带规格和SKU的商品信息
 * Created by macro on 2018/8/2.
 */
@Getter
@Setter
public class CartProduct extends PmsProduct {
    @Schema(description = "商品属性列表")
    private List<PmsProductAttribute> productAttributeList;
    @Schema(description = "商品SKU库存列表")
    private List<PmsSkuStock> skuStockList;
}
