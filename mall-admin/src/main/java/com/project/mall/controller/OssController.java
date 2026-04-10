package com.project.mall.controller;


import com.project.mall.common.api.CommonResult;
import com.project.mall.dto.OssCallbackResult;
import com.project.mall.dto.OssPolicyResult;
import com.project.mall.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Oss閻庣數顢婇挅鍕偓娑櫭崑宥囩不閿涘嫭鍊濩ontroller
 * Created by macro on 2018/4/26.
 */
@Controller
@RequestMapping("/aliyun/oss")
public class OssController {
    @Autowired
    private OssService ossService;

    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OssPolicyResult> policy() {
        OssPolicyResult result = ossService.policy();
        return CommonResult.success(result);
    }

    @RequestMapping(value = "callback", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<OssCallbackResult> callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return CommonResult.success(ossCallbackResult);
    }

}
