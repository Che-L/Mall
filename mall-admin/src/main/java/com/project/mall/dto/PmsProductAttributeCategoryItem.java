package com.project.mall.dto;

import com.project.mall.model.PmsProductAttribute;
import com.project.mall.model.PmsProductAttributeCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 鐢附婀佺仦鐐?褏娈戦崯鍡楁惂鐏炵偞?褍鍨庣猾?
 * Created by macro on 2018/5/24.
 */
public class PmsProductAttributeCategoryItem extends PmsProductAttributeCategory {
    @Getter
    @Setter
    private List<PmsProductAttribute> productAttributeList;
}
