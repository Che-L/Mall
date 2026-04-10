package com.project.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.project.mall.dao.*;
import com.project.mall.dto.PmsProductParam;
import com.project.mall.dto.PmsProductQueryParam;
import com.project.mall.dto.PmsProductResult;
import com.project.mall.mapper.*;
import com.project.mall.model.*;
import com.project.mall.service.PmsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 鍟嗗搧绠＄悊Service瀹炵幇绫?
 * Created by macro on 2018/4/26.
 */
@Service
public class PmsProductServiceImpl implements PmsProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsProductServiceImpl.class);
    @Autowired
    private PmsProductMapper productMapper;
    @Autowired
    private PmsMemberPriceDao memberPriceDao;
    @Autowired
    private PmsMemberPriceMapper memberPriceMapper;
    @Autowired
    private PmsProductLadderDao productLadderDao;
    @Autowired
    private PmsProductLadderMapper productLadderMapper;
    @Autowired
    private PmsProductFullReductionDao productFullReductionDao;
    @Autowired
    private PmsProductFullReductionMapper productFullReductionMapper;
    @Autowired
    private PmsSkuStockDao skuStockDao;
    @Autowired
    private PmsSkuStockMapper skuStockMapper;
    @Autowired
    private PmsProductAttributeValueDao productAttributeValueDao;
    @Autowired
    private PmsProductAttributeValueMapper productAttributeValueMapper;
    @Autowired
    private CmsSubjectProductRelationDao subjectProductRelationDao;
    @Autowired
    private CmsSubjectProductRelationMapper subjectProductRelationMapper;
    @Autowired
    private CmsPrefrenceAreaProductRelationDao prefrenceAreaProductRelationDao;
    @Autowired
    private CmsPrefrenceAreaProductRelationMapper prefrenceAreaProductRelationMapper;
    @Autowired
    private PmsProductDao productDao;
    @Autowired
    private PmsProductVertifyRecordDao productVertifyRecordDao;

    @Override
    public int create(PmsProductParam productParam) {
        int count;
        //鍒涘缓鍟嗗搧
        PmsProduct product = productParam;
        product.setId(null);
        productMapper.insertSelective(product);
        //鏍规嵁淇冮攢绫诲瀷璁剧疆浠锋牸锛氫細鍛樹环鏍笺?侀樁姊环鏍笺?佹弧鍑忎环鏍?
        Long productId = product.getId();
        //浼氬憳浠锋牸
        relateAndInsertList(memberPriceDao, productParam.getMemberPriceList(), productId);
        //闃舵浠锋牸
        relateAndInsertList(productLadderDao, productParam.getProductLadderList(), productId);
        //婊″噺浠锋牸
        relateAndInsertList(productFullReductionDao, productParam.getProductFullReductionList(), productId);
        //澶勭悊sku鐨勭紪鐮?
        handleSkuStockCode(productParam.getSkuStockList(),productId);
        //娣诲姞sku搴撳瓨淇℃伅
        relateAndInsertList(skuStockDao, productParam.getSkuStockList(), productId);
        //娣诲姞鍟嗗搧鍙傛暟,娣诲姞鑷畾涔夊晢鍝佽鏍?
        relateAndInsertList(productAttributeValueDao, productParam.getProductAttributeValueList(), productId);
        //鍏宠仈涓撻
        relateAndInsertList(subjectProductRelationDao, productParam.getSubjectProductRelationList(), productId);
        //鍏宠仈浼橀??
        relateAndInsertList(prefrenceAreaProductRelationDao, productParam.getPrefrenceAreaProductRelationList(), productId);
        count = 1;
        return count;
    }

    private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
        if(CollectionUtils.isEmpty(skuStockList))return;
        for(int i=0;i<skuStockList.size();i++){
            PmsSkuStock skuStock = skuStockList.get(i);
            if(StrUtil.isEmpty(skuStock.getSkuCode())){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                //鏃ユ湡
                sb.append(sdf.format(new Date()));
                //鍥涗綅鍟嗗搧id
                sb.append(String.format("%04d", productId));
                //涓変綅绱㈠紩id
                sb.append(String.format("%03d", i+1));
                skuStock.setSkuCode(sb.toString());
            }
        }
    }

    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        return productDao.getUpdateInfo(id);
    }

    @Override
    public int update(Long id, PmsProductParam productParam) {
        int count;
        //鏇存柊鍟嗗搧淇℃伅
        PmsProduct product = productParam;
        product.setId(id);
        productMapper.updateByPrimaryKeySelective(product);
        //浼氬憳浠锋牸
        PmsMemberPriceExample pmsMemberPriceExample = new PmsMemberPriceExample();
        pmsMemberPriceExample.createCriteria().andProductIdEqualTo(id);
        memberPriceMapper.deleteByExample(pmsMemberPriceExample);
        relateAndInsertList(memberPriceDao, productParam.getMemberPriceList(), id);
        //闃舵浠锋牸
        PmsProductLadderExample ladderExample = new PmsProductLadderExample();
        ladderExample.createCriteria().andProductIdEqualTo(id);
        productLadderMapper.deleteByExample(ladderExample);
        relateAndInsertList(productLadderDao, productParam.getProductLadderList(), id);
        //婊″噺浠锋牸
        PmsProductFullReductionExample fullReductionExample = new PmsProductFullReductionExample();
        fullReductionExample.createCriteria().andProductIdEqualTo(id);
        productFullReductionMapper.deleteByExample(fullReductionExample);
        relateAndInsertList(productFullReductionDao, productParam.getProductFullReductionList(), id);
        //淇敼sku搴撳瓨淇℃伅
        handleUpdateSkuStockList(id, productParam);
        //淇敼鍟嗗搧鍙傛暟,娣诲姞鑷畾涔夊晢鍝佽鏍?
        PmsProductAttributeValueExample productAttributeValueExample = new PmsProductAttributeValueExample();
        productAttributeValueExample.createCriteria().andProductIdEqualTo(id);
        productAttributeValueMapper.deleteByExample(productAttributeValueExample);
        relateAndInsertList(productAttributeValueDao, productParam.getProductAttributeValueList(), id);
        //鍏宠仈涓撻
        CmsSubjectProductRelationExample subjectProductRelationExample = new CmsSubjectProductRelationExample();
        subjectProductRelationExample.createCriteria().andProductIdEqualTo(id);
        subjectProductRelationMapper.deleteByExample(subjectProductRelationExample);
        relateAndInsertList(subjectProductRelationDao, productParam.getSubjectProductRelationList(), id);
        //鍏宠仈浼橀??
        CmsPrefrenceAreaProductRelationExample prefrenceAreaExample = new CmsPrefrenceAreaProductRelationExample();
        prefrenceAreaExample.createCriteria().andProductIdEqualTo(id);
        prefrenceAreaProductRelationMapper.deleteByExample(prefrenceAreaExample);
        relateAndInsertList(prefrenceAreaProductRelationDao, productParam.getPrefrenceAreaProductRelationList(), id);
        count = 1;
        return count;
    }

    private void handleUpdateSkuStockList(Long id, PmsProductParam productParam) {
        //褰撳墠鐨剆ku淇℃伅
        List<PmsSkuStock> currSkuList = productParam.getSkuStockList();
        //褰撳墠娌℃湁sku鐩存帴鍒犻櫎
        if(CollUtil.isEmpty(currSkuList)){
            PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
            skuStockExample.createCriteria().andProductIdEqualTo(id);
            skuStockMapper.deleteByExample(skuStockExample);
            return;
        }
        //鑾峰彇鍒濆sku淇℃伅
        PmsSkuStockExample skuStockExample = new PmsSkuStockExample();
        skuStockExample.createCriteria().andProductIdEqualTo(id);
        List<PmsSkuStock> oriStuList = skuStockMapper.selectByExample(skuStockExample);
        //鑾峰彇鏂板sku淇℃伅
        List<PmsSkuStock> insertSkuList = currSkuList.stream().filter(item->item.getId()==null).collect(Collectors.toList());
        //鑾峰彇闇?瑕佹洿鏂扮殑sku淇℃伅
        List<PmsSkuStock> updateSkuList = currSkuList.stream().filter(item->item.getId()!=null).collect(Collectors.toList());
        List<Long> updateSkuIds = updateSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
        //鑾峰彇闇?瑕佸垹闄ょ殑sku淇℃伅
        List<PmsSkuStock> removeSkuList = oriStuList.stream().filter(item-> !updateSkuIds.contains(item.getId())).collect(Collectors.toList());
        handleSkuStockCode(insertSkuList,id);
        handleSkuStockCode(updateSkuList,id);
        //鏂板sku
        if(CollUtil.isNotEmpty(insertSkuList)){
            relateAndInsertList(skuStockDao, insertSkuList, id);
        }
        //鍒犻櫎sku
        if(CollUtil.isNotEmpty(removeSkuList)){
            List<Long> removeSkuIds = removeSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
            PmsSkuStockExample removeExample = new PmsSkuStockExample();
            removeExample.createCriteria().andIdIn(removeSkuIds);
            skuStockMapper.deleteByExample(removeExample);
        }
        //淇敼sku
        if(CollUtil.isNotEmpty(updateSkuList)){
            for (PmsSkuStock pmsSkuStock : updateSkuList) {
                skuStockMapper.updateByPrimaryKeySelective(pmsSkuStock);
            }
        }

    }

    @Override
    public List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (productQueryParam.getPublishStatus() != null) {
            criteria.andPublishStatusEqualTo(productQueryParam.getPublishStatus());
        }
        if (productQueryParam.getVerifyStatus() != null) {
            criteria.andVerifyStatusEqualTo(productQueryParam.getVerifyStatus());
        }
        if (!StrUtil.isEmpty(productQueryParam.getKeyword())) {
            criteria.andNameLike("%" + productQueryParam.getKeyword() + "%");
        }
        if (!StrUtil.isEmpty(productQueryParam.getProductSn())) {
            criteria.andProductSnEqualTo(productQueryParam.getProductSn());
        }
        if (productQueryParam.getBrandId() != null) {
            criteria.andBrandIdEqualTo(productQueryParam.getBrandId());
        }
        if (productQueryParam.getProductCategoryId() != null) {
            criteria.andProductCategoryIdEqualTo(productQueryParam.getProductCategoryId());
        }
        return productMapper.selectByExample(productExample);
    }

    @Override
    public int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {
        PmsProduct product = new PmsProduct();
        product.setVerifyStatus(verifyStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        List<PmsProductVertifyRecord> list = new ArrayList<>();
        int count = productMapper.updateByExampleSelective(product, example);
        //淇敼瀹屽鏍哥姸鎬佸悗鎻掑叆瀹℃牳璁板綍
        for (Long id : ids) {
            PmsProductVertifyRecord record = new PmsProductVertifyRecord();
            record.setProductId(id);
            record.setCreateTime(new Date());
            record.setDetail(detail);
            record.setStatus(verifyStatus);
            record.setVertifyMan("test");
            list.add(record);
        }
        productVertifyRecordDao.insertList(list);
        return count;
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
        PmsProduct record = new PmsProduct();
        record.setPublishStatus(publishStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        PmsProduct record = new PmsProduct();
        record.setRecommandStatus(recommendStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        PmsProduct record = new PmsProduct();
        record.setNewStatus(newStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        PmsProduct record = new PmsProduct();
        record.setDeleteStatus(deleteStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<PmsProduct> list(String keyword) {
        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if(!StrUtil.isEmpty(keyword)){
            criteria.andNameLike("%" + keyword + "%");
            productExample.or().andDeleteStatusEqualTo(0).andProductSnLike("%" + keyword + "%");
        }
        return productMapper.selectByExample(productExample);
    }

    /**
     * 寤虹珛鍜屾彃鍏ュ叧绯昏〃鎿嶄綔
     *
     * @param dao       鍙互鎿嶄綔鐨刣ao
     * @param dataList  瑕佹彃鍏ョ殑鏁版嵁
     * @param productId 寤虹珛鍏崇郴鐨刬d
     */
    private void relateAndInsertList(Object dao, List dataList, Long productId) {
        try {
            if (CollectionUtils.isEmpty(dataList)) return;
            for (Object item : dataList) {
                Method setId = item.getClass().getMethod("setId", Long.class);
                setId.invoke(item, (Long) null);
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(item, productId);
            }
            Method insertList = dao.getClass().getMethod("insertList", List.class);
            insertList.invoke(dao, dataList);
        } catch (Exception e) {
            LOGGER.warn("鍒涘缓浜у搧鍑洪敊:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
