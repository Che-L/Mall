package com.project.mall.controller;

import com.project.mall.common.api.CommonResult;
import com.project.mall.model.UmsMemberLevel;
import com.project.mall.service.UmsMemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 娴兼艾鎲崇粵澶岄獓缁狅紕鎮奀ontroller
 * Created by macro on 2018/4/26.
 */
@Controller
@RequestMapping("/memberLevel")
public class UmsMemberLevelController {
    @Autowired
    private UmsMemberLevelService memberLevelService;
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsMemberLevel>> list(@RequestParam("defaultStatus") Integer defaultStatus) {
        List<UmsMemberLevel> memberLevelList = memberLevelService.list(defaultStatus);
        return CommonResult.success(memberLevelList);
    }
}
