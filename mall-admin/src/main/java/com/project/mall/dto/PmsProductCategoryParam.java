package com.project.mall.dto;

import com.project.mall.validator.FlagValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 婵烇綀顕ф慨鐐哄即鐎涙ɑ鐓€濞存籂鍐╂儌闁告帒妫涚悮顐︽儍閸曨偄妫橀柡?
 * Created by macro on 2018/4/26.
 */
@Data
@EqualsAndHashCode
public class PmsProductCategoryParam {
    private Long parentId;
    @NotEmpty
    private String name;
    private String productUnit;
    @FlagValidator(value = {"0","1"},message = "闁绘鍩?娴ｇ娑ч柤铏灊鐠?闁?")
    private Integer navStatus;
    @FlagValidator(value = {"0","1"},message = "闁绘鍩?娴ｇ娑ч柤铏灊鐠?闁?")
    private Integer showStatus;
    @Min(value = 0)
    private Integer sort;
    private String icon;
    private String keywords;
    private String description;
    private List<Long> productAttributeIdList;
}
