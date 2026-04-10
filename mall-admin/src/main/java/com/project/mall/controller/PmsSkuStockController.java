package com.project.mall.controller;

import com.project.mall.common.api.CommonResult;
import com.project.mall.model.PmsSkuStock;
import com.project.mall.service.PmsSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 閸熷棗鎼KU鎼存挸鐡ㄧ粻锛勬倞Controller
 * Created by macro on 2018/4/27.
 */
@Controller
@RequestMapping("/sku")
public class PmsSkuStockController {
    @Autowired
    private PmsSkuStockService skuStockService;

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsSkuStock>> getList(@PathVariable Long pid, @RequestParam(value = "keyword",required = false) String keyword) {
        List<PmsSkuStock> skuStockList = skuStockService.getList(pid, keyword);
        return CommonResult.success(skuStockList);
    }
    @RequestMapping(value ="/update/{pid}",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long pid,@RequestBody List<PmsSkuStock> skuStockList){
        int count = skuStockService.update(pid,skuStockList);
        if(count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }
}
