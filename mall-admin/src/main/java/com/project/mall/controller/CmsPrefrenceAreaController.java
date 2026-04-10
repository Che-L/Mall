package com.project.mall.controller;

import com.project.mall.common.api.CommonResult;
import com.project.mall.model.CmsPrefrenceArea;
import com.project.mall.service.CmsPrefrenceAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 閸熷棗鎼ф导姗€鈧顓搁悶鍜皁ntroller
 * Created by macro on 2018/6/1.
 */
@Controller
@RequestMapping("/prefrenceArea")
public class CmsPrefrenceAreaController {
    @Autowired
    private CmsPrefrenceAreaService prefrenceAreaService;
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<CmsPrefrenceArea>> listAll() {
        List<CmsPrefrenceArea> prefrenceAreaList = prefrenceAreaService.listAll();
        return CommonResult.success(prefrenceAreaList);
    }
}
