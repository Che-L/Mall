package com.project.mall.search.controller;

import com.project.mall.common.api.CommonPage;
import com.project.mall.common.api.CommonResult;
import com.project.mall.search.domain.EsProduct;
import com.project.mall.search.domain.EsProductRelatedInfo;
import com.project.mall.search.service.EsProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Tag(name = "EsProductController", description = "搜索商品管理")
@RequestMapping("/esProduct")
public class EsProductController {

    @Autowired
    private EsProductService esProductService;

    @Operation(summary = "导入所有数据库中商品到 ES")
    @RequestMapping(value = "/importAll", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> importAllList() {
        int count = esProductService.importAll();
        return CommonResult.success(count);
    }

    @Operation(summary = "根据 id 删除商品")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> delete(@PathVariable Long id) {
        esProductService.delete(id);
        return CommonResult.success(null);
    }

    @Operation(summary = "根据 id 批量删除商品")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> deleteBatch(@RequestParam("ids") List<Long> ids) {
        esProductService.delete(ids);
        return CommonResult.success(null);
    }

    @Operation(summary = "根据 id 创建商品文档")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<EsProduct> create(@PathVariable Long id) {
        EsProduct esProduct = esProductService.create(id);
        if (esProduct != null) {
            return CommonResult.success(esProduct);
        }
        return CommonResult.failed();
    }

    @Operation(summary = "简单搜索")
    @RequestMapping(value = "/search/simple", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<EsProduct>> searchSimple(@RequestParam(required = false) String keyword,
                                                            @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                            @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }

    @Operation(summary = "综合搜索、筛选、排序")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<EsProduct>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long productCategoryId,
            @RequestParam(required = false, defaultValue = "0") Integer pageNum,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize,
            @Parameter(description = "0 相关度；1 新品；2 销量；3 价格升序；4 价格降序")
            @RequestParam(required = false, defaultValue = "0") Integer sort) {
        Page<EsProduct> esProductPage = esProductService.search(keyword, brandId, productCategoryId, pageNum, pageSize, sort);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }

    @Operation(summary = "根据商品 id 推荐商品")
    @RequestMapping(value = "/recommend/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<EsProduct>> recommend(@PathVariable Long id,
                                                         @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                         @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.recommend(id, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }

    @Operation(summary = "获取搜索相关品牌、分类及筛选属性")
    @RequestMapping(value = "/search/relate", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<EsProductRelatedInfo> searchRelatedInfo(@RequestParam(required = false) String keyword) {
        EsProductRelatedInfo productRelatedInfo = esProductService.searchRelatedInfo(keyword);
        return CommonResult.success(productRelatedInfo);
    }
}
