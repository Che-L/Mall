package com.project.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.project.mall.dao.PmsSkuStockDao;
import com.project.mall.mapper.PmsSkuStockMapper;
import com.project.mall.model.PmsSkuStock;
import com.project.mall.model.PmsSkuStockExample;
import com.project.mall.service.PmsSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 鍟嗗搧SKU搴撳瓨绠＄悊Service瀹炵幇绫?
 * Created by macro on 2018/4/27.
 */
@Service
public class PmsSkuStockServiceImpl implements PmsSkuStockService {
    @Autowired
    private PmsSkuStockMapper skuStockMapper;
    @Autowired
    private PmsSkuStockDao skuStockDao;

    @Override
    public List<PmsSkuStock> getList(Long pid, String keyword) {
        PmsSkuStockExample example = new PmsSkuStockExample();
        PmsSkuStockExample.Criteria criteria = example.createCriteria().andProductIdEqualTo(pid);
        if (!StrUtil.isEmpty(keyword)) {
            criteria.andSkuCodeLike("%" + keyword + "%");
        }
        return skuStockMapper.selectByExample(example);
    }

    @Override
    public int update(Long pid, List<PmsSkuStock> skuStockList) {
        return skuStockDao.replaceList(skuStockList);
    }
}
