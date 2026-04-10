package com.project.mall.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/**
 * 闁活潿鍔嶉崺娑樷枖閵娿儱鏂€闁告瑥鍊归弳?
 * Created by macro on 2018/4/26.
 */
@Getter
@Setter
public class UmsAdminParam {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private String icon;
    @Email
    private String email;
    private String nickName;
    private String note;
}
