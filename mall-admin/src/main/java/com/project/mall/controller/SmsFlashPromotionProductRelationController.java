package com.project.mall.controller;

import com.project.mall.common.api.CommonPage;
import com.project.mall.common.api.CommonResult;
import com.project.mall.dto.SmsFlashPromotionProduct;
import com.project.mall.model.SmsFlashPromotionProductRelation;
import com.project.mall.service.SmsFlashPromotionProductRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 闂勬劖妞傜拹顓炴嫲閸熷棗鎼ч崗宕囬兇缁狅紕鎮奀ontroller
 * Created by macro on 2018/11/16.
 */
@Controller
@RequestMapping("/flashProductRelation")
public class SmsFlashPromotionProductRelationController {
    @Autowired
    private SmsFlashPromotionProductRelationService relationService;
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody List<SmsFlashPromotionProductRelation> relationList) {
        int count = relationService.create(relationList);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody SmsFlashPromotionProductRelation relation) {
        int count = relationService.update(id, relation);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = relationService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<SmsFlashPromotionProductRelation> getItem(@PathVariable Long id) {
        SmsFlashPromotionProductRelation relation = relationService.getItem(id);
        return CommonResult.success(relation);
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<SmsFlashPromotionProduct>> list(@RequestParam(value = "flashPromotionId") Long flashPromotionId,
                                                                   @RequestParam(value = "flashPromotionSessionId") Long flashPromotionSessionId,
                                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SmsFlashPromotionProduct> flashPromotionProductList = relationService.list(flashPromotionId, flashPromotionSessionId, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(flashPromotionProductList));
    }
}
