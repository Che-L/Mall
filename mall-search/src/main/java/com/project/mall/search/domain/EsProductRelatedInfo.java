package com.project.mall.search.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
@Schema(description = "搜索关联信息：品牌、分类、属性")
public class EsProductRelatedInfo {
    private List<String> brandNames;
    private List<String> productCategoryNames;
    private List<ProductAttr> productAttrs;

    @Data
    @EqualsAndHashCode
    @Schema(description = "商品属性聚合项")
    public static class ProductAttr {
        private Long attrId;
        private String attrName;
        private List<String> attrValues;
    }
}
