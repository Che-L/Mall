package com.project.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.project.mall.dao.SmsCouponDao;
import com.project.mall.dao.SmsCouponProductCategoryRelationDao;
import com.project.mall.dao.SmsCouponProductRelationDao;
import com.project.mall.dto.SmsCouponParam;
import com.project.mall.mapper.SmsCouponMapper;
import com.project.mall.mapper.SmsCouponProductCategoryRelationMapper;
import com.project.mall.mapper.SmsCouponProductRelationMapper;
import com.project.mall.model.*;
import com.project.mall.service.SmsCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 浼樻儬鍒哥鐞哠ervice瀹炵幇绫?
 * Created by macro on 2018/8/28.
 */
@Service
public class SmsCouponServiceImpl implements SmsCouponService {
    @Autowired
    private SmsCouponMapper couponMapper;
    @Autowired
    private SmsCouponProductRelationMapper productRelationMapper;
    @Autowired
    private SmsCouponProductCategoryRelationMapper productCategoryRelationMapper;
    @Autowired
    private SmsCouponProductRelationDao productRelationDao;
    @Autowired
    private SmsCouponProductCategoryRelationDao productCategoryRelationDao;
    @Autowired
    private SmsCouponDao couponDao;
    @Override
    public int create(SmsCouponParam couponParam) {
        couponParam.setCount(couponParam.getPublishCount());
        couponParam.setUseCount(0);
        couponParam.setReceiveCount(0);
        //鎻掑叆浼樻儬鍒歌〃
        int count = couponMapper.insert(couponParam);
        //鎻掑叆浼樻儬鍒稿拰鍟嗗搧鍏崇郴琛?
        if(couponParam.getUseType().equals(2)){
            for(SmsCouponProductRelation productRelation:couponParam.getProductRelationList()){
                productRelation.setCouponId(couponParam.getId());
            }
            productRelationDao.insertList(couponParam.getProductRelationList());
        }
        //鎻掑叆浼樻儬鍒稿拰鍟嗗搧鍒嗙被鍏崇郴琛?
        if(couponParam.getUseType().equals(1)){
            for (SmsCouponProductCategoryRelation couponProductCategoryRelation : couponParam.getProductCategoryRelationList()) {
                couponProductCategoryRelation.setCouponId(couponParam.getId());
            }
            productCategoryRelationDao.insertList(couponParam.getProductCategoryRelationList());
        }
        return count;
    }

    @Override
    public int delete(Long id) {
        //鍒犻櫎浼樻儬鍒?
        int count = couponMapper.deleteByPrimaryKey(id);
        //鍒犻櫎鍟嗗搧鍏宠仈
        deleteProductRelation(id);
        //鍒犻櫎鍟嗗搧鍒嗙被鍏宠仈
        deleteProductCategoryRelation(id);
        return count;
    }

    private void deleteProductCategoryRelation(Long id) {
        SmsCouponProductCategoryRelationExample productCategoryRelationExample = new SmsCouponProductCategoryRelationExample();
        productCategoryRelationExample.createCriteria().andCouponIdEqualTo(id);
        productCategoryRelationMapper.deleteByExample(productCategoryRelationExample);
    }

    private void deleteProductRelation(Long id) {
        SmsCouponProductRelationExample productRelationExample = new SmsCouponProductRelationExample();
        productRelationExample.createCriteria().andCouponIdEqualTo(id);
        productRelationMapper.deleteByExample(productRelationExample);
    }

    @Override
    public int update(Long id, SmsCouponParam couponParam) {
        couponParam.setId(id);
        int count =couponMapper.updateByPrimaryKey(couponParam);
        //鍒犻櫎鍚庢彃鍏ヤ紭鎯犲埜鍜屽晢鍝佸叧绯昏〃
        if(couponParam.getUseType().equals(2)){
            for(SmsCouponProductRelation productRelation:couponParam.getProductRelationList()){
                productRelation.setCouponId(couponParam.getId());
            }
            deleteProductRelation(id);
            productRelationDao.insertList(couponParam.getProductRelationList());
        }
        //鍒犻櫎鍚庢彃鍏ヤ紭鎯犲埜鍜屽晢鍝佸垎绫诲叧绯昏〃
        if(couponParam.getUseType().equals(1)){
            for (SmsCouponProductCategoryRelation couponProductCategoryRelation : couponParam.getProductCategoryRelationList()) {
                couponProductCategoryRelation.setCouponId(couponParam.getId());
            }
            deleteProductCategoryRelation(id);
            productCategoryRelationDao.insertList(couponParam.getProductCategoryRelationList());
        }
        return count;
    }

    @Override
    public List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum) {
        SmsCouponExample example = new SmsCouponExample();
        SmsCouponExample.Criteria criteria = example.createCriteria();
        if(!StrUtil.isEmpty(name)){
            criteria.andNameLike("%"+name+"%");
        }
        if(type!=null){
            criteria.andTypeEqualTo(type);
        }
        PageHelper.startPage(pageNum,pageSize);
        return couponMapper.selectByExample(example);
    }

    @Override
    public SmsCouponParam getItem(Long id) {
        return couponDao.getItem(id);
    }
}
