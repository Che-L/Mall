package com.project.mall.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotEmpty;

/**
 * 闁活潿鍔嶉崺娑㈡儌鐠囪尙绉块柛娆忓€归弳?
 * Created by macro on 2018/4/26.
 */
@Data
@EqualsAndHashCode
public class UmsAdminLoginParam {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
