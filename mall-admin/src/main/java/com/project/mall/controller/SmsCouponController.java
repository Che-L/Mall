package com.project.mall.controller;

import com.project.mall.common.api.CommonPage;
import com.project.mall.common.api.CommonResult;
import com.project.mall.dto.SmsCouponParam;
import com.project.mall.model.SmsCoupon;
import com.project.mall.service.SmsCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 娴兼ɑ鍎崚鍝ヮ吀閻炲挵ontroller
 * Created by macro on 2018/8/28.
 */
@Controller
@RequestMapping("/coupon")
public class SmsCouponController {
    @Autowired
    private SmsCouponService couponService;
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@RequestBody SmsCouponParam couponParam) {
        int count = couponService.create(couponParam);
        if(count>0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = couponService.delete(id);
        if(count>0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id,@RequestBody SmsCouponParam couponParam) {
        int count = couponService.update(id,couponParam);
        if(count>0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<SmsCoupon>> list(
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "type",required = false) Integer type,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SmsCoupon> couponList = couponService.list(name,type,pageSize,pageNum);
        return CommonResult.success(CommonPage.restPage(couponList));
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<SmsCouponParam> getItem(@PathVariable Long id) {
        SmsCouponParam couponParam = couponService.getItem(id);
        return CommonResult.success(couponParam);
    }
}
