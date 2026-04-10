package com.project.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.project.mall.dao.PmsProductCategoryAttributeRelationDao;
import com.project.mall.dao.PmsProductCategoryDao;
import com.project.mall.dto.PmsProductCategoryParam;
import com.project.mall.dto.PmsProductCategoryWithChildrenItem;
import com.project.mall.mapper.PmsProductCategoryAttributeRelationMapper;
import com.project.mall.mapper.PmsProductCategoryMapper;
import com.project.mall.mapper.PmsProductMapper;
import com.project.mall.model.*;
import com.project.mall.service.PmsProductCategoryService;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 鍟嗗搧鍒嗙被绠＄悊Service瀹炵幇绫?
 * Created by macro on 2018/4/26.
 */
@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {
    @Autowired
    private PmsProductCategoryMapper productCategoryMapper;
    @Autowired
    private PmsProductMapper productMapper;
    @Autowired
    private PmsProductCategoryAttributeRelationDao productCategoryAttributeRelationDao;
    @Autowired
    private PmsProductCategoryAttributeRelationMapper productCategoryAttributeRelationMapper;
    @Autowired
    private PmsProductCategoryDao productCategoryDao;
    @Override
    public int create(PmsProductCategoryParam pmsProductCategoryParam) {
        PmsProductCategory productCategory = new PmsProductCategory();
        productCategory.setProductCount(0);
        BeanUtils.copyProperties(pmsProductCategoryParam, productCategory);
        //娌℃湁鐖跺垎绫绘椂涓轰竴绾у垎绫?
        setCategoryLevel(productCategory);
        int count = productCategoryMapper.insertSelective(productCategory);
        //鍒涘缓绛涢?夊睘鎬у叧鑱?
        List<Long> productAttributeIdList = pmsProductCategoryParam.getProductAttributeIdList();
        if(!CollectionUtils.isEmpty(productAttributeIdList)){
            insertRelationList(productCategory.getId(), productAttributeIdList);
        }
        return count;
    }

    /**
     * 鎵归噺鎻掑叆鍟嗗搧鍒嗙被涓庣瓫閫夊睘鎬у叧绯昏〃
     * @param productCategoryId 鍟嗗搧鍒嗙被id
     * @param productAttributeIdList 鐩稿叧鍟嗗搧绛涢?夊睘鎬d闆嗗悎
     */
    private void insertRelationList(Long productCategoryId, List<Long> productAttributeIdList) {
        List<PmsProductCategoryAttributeRelation> relationList = new ArrayList<>();
        for (Long productAttrId : productAttributeIdList) {
            PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
            relation.setProductAttributeId(productAttrId);
            relation.setProductCategoryId(productCategoryId);
            relationList.add(relation);
        }
        productCategoryAttributeRelationDao.insertList(relationList);
    }

    @Override
    public int update(Long id, PmsProductCategoryParam pmsProductCategoryParam) {
        PmsProductCategory productCategory = new PmsProductCategory();
        productCategory.setId(id);
        BeanUtils.copyProperties(pmsProductCategoryParam, productCategory);
        setCategoryLevel(productCategory);
        //鏇存柊鍟嗗搧鍒嗙被鏃惰鏇存柊鍟嗗搧涓殑鍚嶇О
        PmsProduct product = new PmsProduct();
        product.setProductCategoryName(productCategory.getName());
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andProductCategoryIdEqualTo(id);
        productMapper.updateByExampleSelective(product,example);
        //鍚屾椂鏇存柊绛涢?夊睘鎬х殑淇℃伅
        if(!CollectionUtils.isEmpty(pmsProductCategoryParam.getProductAttributeIdList())){
            PmsProductCategoryAttributeRelationExample relationExample = new PmsProductCategoryAttributeRelationExample();
            relationExample.createCriteria().andProductCategoryIdEqualTo(id);
            productCategoryAttributeRelationMapper.deleteByExample(relationExample);
            insertRelationList(id,pmsProductCategoryParam.getProductAttributeIdList());
        }else{
            PmsProductCategoryAttributeRelationExample relationExample = new PmsProductCategoryAttributeRelationExample();
            relationExample.createCriteria().andProductCategoryIdEqualTo(id);
            productCategoryAttributeRelationMapper.deleteByExample(relationExample);
        }
        return productCategoryMapper.updateByPrimaryKeySelective(productCategory);
    }

    @Override
    public List<PmsProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        return productCategoryMapper.selectByExample(example);
    }

    @Override
    public int delete(Long id) {
        return productCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PmsProductCategory getItem(Long id) {
        return productCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateNavStatus(List<Long> ids, Integer navStatus) {
        PmsProductCategory productCategory = new PmsProductCategory();
        productCategory.setNavStatus(navStatus);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.createCriteria().andIdIn(ids);
        return productCategoryMapper.updateByExampleSelective(productCategory, example);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        PmsProductCategory productCategory = new PmsProductCategory();
        productCategory.setShowStatus(showStatus);
        PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.createCriteria().andIdIn(ids);
        return productCategoryMapper.updateByExampleSelective(productCategory, example);
    }

    @Override
    public List<PmsProductCategoryWithChildrenItem> listWithChildren() {
        return productCategoryDao.listWithChildren();
    }

    /**
     * 鏍规嵁鍒嗙被鐨刾arentId璁剧疆鍒嗙被鐨刲evel
     */
    private void setCategoryLevel(PmsProductCategory productCategory) {
        //娌℃湁鐖跺垎绫绘椂涓轰竴绾у垎绫?
        if (productCategory.getParentId() == 0) {
            productCategory.setLevel(0);
        } else {
            //鏈夌埗鍒嗙被鏃堕?夋嫨鏍规嵁鐖跺垎绫籰evel璁剧疆
            PmsProductCategory parentCategory = productCategoryMapper.selectByPrimaryKey(productCategory.getParentId());
            if (parentCategory != null) {
                productCategory.setLevel(parentCategory.getLevel() + 1);
            } else {
                productCategory.setLevel(0);
            }
        }
    }
}
