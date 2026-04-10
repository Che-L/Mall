package com.project.mall.dto;

import com.project.mall.model.PmsProductCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 閸栧懎鎯堢€涙劗楠囬崚鍡欒閻ㄥ嫬鏅㈤崫浣稿瀻缁?
 * Created by macro on 2018/5/25.
 */
public class PmsProductCategoryWithChildrenItem extends PmsProductCategory {
    @Getter
    @Setter
    private List<PmsProductCategory> children;
}
