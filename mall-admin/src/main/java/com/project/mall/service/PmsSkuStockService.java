package com.project.mall.service;

import com.project.mall.model.PmsSkuStock;

import java.util.List;

/**
 * 鍟嗗搧SKU搴撳瓨绠＄悊Service
 * Created by macro on 2018/4/27.
 */
public interface PmsSkuStockService {
    /**
     * 鏍规嵁浜у搧id鍜宻kuCode鍏抽敭瀛楁ā绯婃悳绱?
     */
    List<PmsSkuStock> getList(Long pid, String keyword);

    /**
     * 鎵归噺鏇存柊鍟嗗搧搴撳瓨淇℃伅
     */
    int update(Long pid, List<PmsSkuStock> skuStockList);
}
