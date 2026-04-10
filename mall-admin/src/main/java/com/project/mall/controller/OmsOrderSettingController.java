package com.project.mall.controller;

import com.project.mall.common.api.CommonResult;
import com.project.mall.model.OmsOrderSetting;
import com.project.mall.service.OmsOrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 鐠併垹宕熺拋鍓х枂缁狅紕鎮奀ontroller
 * Created by macro on 2018/10/16.
 */
@Controller
@RequestMapping("/orderSetting")
public class OmsOrderSettingController {
    @Autowired
    private OmsOrderSettingService orderSettingService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OmsOrderSetting> getItem(@PathVariable Long id) {
        OmsOrderSetting orderSetting = orderSettingService.getItem(id);
        return CommonResult.success(orderSetting);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody OmsOrderSetting orderSetting) {
        int count = orderSettingService.update(id,orderSetting);
        if(count>0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
