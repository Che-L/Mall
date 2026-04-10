package com.project.mall.controller;

import com.project.mall.common.api.CommonPage;
import com.project.mall.common.api.CommonResult;
import com.project.mall.model.SmsCouponHistory;
import com.project.mall.service.SmsCouponHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 娴兼ɑ鍎崚鎼侇暙閸欐牞顔囪ぐ鏇狀吀閻炲挵ontroller
 * Created by macro on 2018/11/6.
 */
@Controller
@RequestMapping("/couponHistory")
public class SmsCouponHistoryController {
    @Autowired
    private SmsCouponHistoryService historyService;
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<SmsCouponHistory>> list(@RequestParam(value = "couponId", required = false) Long couponId,
                                                           @RequestParam(value = "useStatus", required = false) Integer useStatus,
                                                           @RequestParam(value = "orderSn", required = false) String orderSn,
                                                           @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SmsCouponHistory> historyList = historyService.list(couponId, useStatus, orderSn, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(historyList));
    }
}
