package com.project.mall.dto;

import com.project.mall.validator.FlagValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

/**
 * 闁告繀鑳舵晶婵堟嫚闁垮婀撮柛娆忓€归弳?
 * Created by macro on 2018/4/26.
 */
@Data
@EqualsAndHashCode
public class PmsBrandParam {
    @NotEmpty
    private String name;
    private String firstLetter;
    @Min(value = 0)
    private Integer sort;
    @FlagValidator(value = {"0","1"}, message = "Factory status must be 0 or 1")
    private Integer factoryStatus;
    @FlagValidator(value = {"0","1"}, message = "Show status must be 0 or 1")
    private Integer showStatus;
    @NotEmpty
    private String logo;
    private String bigPic;
    private String brandStory;
}
