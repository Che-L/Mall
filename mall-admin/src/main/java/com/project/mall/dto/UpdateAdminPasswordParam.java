package com.project.mall.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;

/**
 * 濞ｅ浂鍠楅弫濂告偨閵婏箑鐓曢柛姘Т閻︽垿鎯嶆担绋挎闁?
 * Created by macro on 2019/10/9.
 */
@Getter
@Setter
public class UpdateAdminPasswordParam {
    @NotEmpty
    private String username;
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;
}
