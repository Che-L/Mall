package com.project.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.project.mall.dao.PmsProductAttributeDao;
import com.project.mall.dto.PmsProductAttributeParam;
import com.project.mall.dto.ProductAttrInfo;
import com.project.mall.mapper.PmsProductAttributeCategoryMapper;
import com.project.mall.mapper.PmsProductAttributeMapper;
import com.project.mall.model.PmsProductAttribute;
import com.project.mall.model.PmsProductAttributeCategory;
import com.project.mall.model.PmsProductAttributeExample;
import com.project.mall.service.PmsProductAttributeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 鍟嗗搧灞炴?х鐞哠ervice瀹炵幇绫?
 * Created by macro on 2018/4/26.
 */
@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {
    @Autowired
    private PmsProductAttributeMapper productAttributeMapper;
    @Autowired
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;
    @Autowired
    private PmsProductAttributeDao productAttributeDao;

    @Override
    public List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andProductAttributeCategoryIdEqualTo(cid).andTypeEqualTo(type);
        return productAttributeMapper.selectByExample(example);
    }

    @Override
    public int create(PmsProductAttributeParam pmsProductAttributeParam) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(pmsProductAttributeParam, pmsProductAttribute);
        int count = productAttributeMapper.insertSelective(pmsProductAttribute);
        //鏂板鍟嗗搧灞炴?т互鍚庨渶瑕佹洿鏂板晢鍝佸睘鎬у垎绫绘暟閲?
        PmsProductAttributeCategory pmsProductAttributeCategory = productAttributeCategoryMapper.selectByPrimaryKey(pmsProductAttribute.getProductAttributeCategoryId());
        if(pmsProductAttribute.getType()==0){
            pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount()+1);
        }else if(pmsProductAttribute.getType()==1){
            pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount()+1);
        }
        productAttributeCategoryMapper.updateByPrimaryKey(pmsProductAttributeCategory);
        return count;
    }

    @Override
    public int update(Long id, PmsProductAttributeParam productAttributeParam) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        pmsProductAttribute.setId(id);
        BeanUtils.copyProperties(productAttributeParam, pmsProductAttribute);
        return productAttributeMapper.updateByPrimaryKeySelective(pmsProductAttribute);
    }

    @Override
    public PmsProductAttribute getItem(Long id) {
        return productAttributeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(List<Long> ids) {
        //鑾峰彇鍒嗙被
        PmsProductAttribute pmsProductAttribute = productAttributeMapper.selectByPrimaryKey(ids.get(0));
        Integer type = pmsProductAttribute.getType();
        PmsProductAttributeCategory pmsProductAttributeCategory = productAttributeCategoryMapper.selectByPrimaryKey(pmsProductAttribute.getProductAttributeCategoryId());
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.createCriteria().andIdIn(ids);
        int count = productAttributeMapper.deleteByExample(example);
        //鍒犻櫎瀹屾垚鍚庝慨鏀规暟閲?
        if(type==0){
            if(pmsProductAttributeCategory.getAttributeCount()>=count){
                pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount()-count);
            }else{
                pmsProductAttributeCategory.setAttributeCount(0);
            }
        }else if(type==1){
            if(pmsProductAttributeCategory.getParamCount()>=count){
                pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount()-count);
            }else{
                pmsProductAttributeCategory.setParamCount(0);
            }
        }
        productAttributeCategoryMapper.updateByPrimaryKey(pmsProductAttributeCategory);
        return count;
    }

    @Override
    public List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId) {
        return productAttributeDao.getProductAttrInfo(productCategoryId);
    }
}
