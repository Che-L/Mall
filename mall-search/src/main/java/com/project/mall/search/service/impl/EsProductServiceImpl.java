package com.project.mall.search.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.FilterAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.NestedAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionScore;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.project.mall.search.dao.EsProductDao;
import com.project.mall.search.domain.EsProduct;
import com.project.mall.search.domain.EsProductRelatedInfo;
import com.project.mall.search.repository.EsProductRepository;
import com.project.mall.search.service.EsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EsProductServiceImpl implements EsProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsProductServiceImpl.class);

    @Autowired
    private EsProductDao productDao;
    @Autowired
    private EsProductRepository productRepository;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public int importAll() {
        List<EsProduct> esProductList = productDao.getAllEsProductList(null);
        Iterable<EsProduct> esProductIterable = productRepository.saveAll(esProductList);
        Iterator<EsProduct> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public EsProduct create(Long id) {
        List<EsProduct> esProductList = productDao.getAllEsProductList(id);
        if (esProductList.size() > 0) {
            return productRepository.save(esProductList.get(0));
        }
        return null;
    }

    @Override
    public void delete(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<EsProduct> esProductList = new ArrayList<>();
            for (Long id : ids) {
                EsProduct esProduct = new EsProduct();
                esProduct.setId(id);
                esProductList.add(esProduct);
            }
            productRepository.deleteAll(esProductList);
        }
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return productRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }

    @Override
    public Page<EsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Query keywordQuery = StrUtil.isEmpty(keyword)
                ? Query.of(q -> q.matchAll(m -> m))
                : functionScoreKeywordQuery(keyword);

        Query rootQuery;
        if (brandId != null || productCategoryId != null) {
            rootQuery = Query.of(q -> q.bool(b -> {
                b.must(keywordQuery);
                if (brandId != null) {
                    b.filter(f -> f.term(t -> t.field("brandId").value(brandId)));
                }
                if (productCategoryId != null) {
                    b.filter(f -> f.term(t -> t.field("productCategoryId").value(productCategoryId)));
                }
                return b;
            }));
        } else {
            rootQuery = keywordQuery;
        }

        NativeQueryBuilder builder = NativeQuery.builder()
                .withQuery(rootQuery)
                .withPageable(pageable);
        applySort(builder, sort);
        NativeQuery searchQuery = builder.build();
        LOGGER.info("search query: {}", searchQuery.getQuery());

        SearchHits<EsProduct> searchHits = elasticsearchOperations.search(searchQuery, EsProduct.class);
        long total = searchHits.getTotalHits();
        if (total <= 0) {
            return new PageImpl<>(ListUtil.empty(), pageable, 0);
        }
        List<EsProduct> list = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, total);
    }

    private static Query functionScoreKeywordQuery(String keyword) {
        return Query.of(q -> q.functionScore(fs -> fs
                .query(qq -> qq.matchAll(ma -> ma))
                .functions(
                        FunctionScore.of(f -> f.filter(fl -> fl.match(m -> m.field("name").query(keyword))).weight(10.0)),
                        FunctionScore.of(f -> f.filter(fl -> fl.match(m -> m.field("subTitle").query(keyword))).weight(5.0)),
                        FunctionScore.of(f -> f.filter(fl -> fl.match(m -> m.field("keywords").query(keyword))).weight(2.0))
                )
                .scoreMode(FunctionScoreMode.Sum)
                .minScore(2.0)
        ));
    }

    private static void applySort(NativeQueryBuilder builder, int sort) {
        switch (sort) {
            case 1 -> builder
                    .withSort(s -> s.field(f -> f.field("id").order(SortOrder.Desc)))
                    .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)));
            case 2 -> builder
                    .withSort(s -> s.field(f -> f.field("sale").order(SortOrder.Desc)))
                    .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)));
            case 3 -> builder
                    .withSort(s -> s.field(f -> f.field("price").order(SortOrder.Asc)))
                    .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)));
            case 4 -> builder
                    .withSort(s -> s.field(f -> f.field("price").order(SortOrder.Desc)))
                    .withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)));
            default -> builder.withSort(s -> s.score(sc -> sc.order(SortOrder.Desc)));
        }
    }

    @Override
    public Page<EsProduct> recommend(Long id, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        List<EsProduct> esProductList = productDao.getAllEsProductList(id);
        if (esProductList.isEmpty()) {
            return new PageImpl<>(ListUtil.empty());
        }
        EsProduct esProduct = esProductList.get(0);
        String keyword = esProduct.getName();
        Long brandId = esProduct.getBrandId();
        Long productCategoryId = esProduct.getProductCategoryId();

        Query functionScore = Query.of(q -> q.functionScore(fs -> fs
                .query(qq -> qq.matchAll(ma -> ma))
                .functions(
                        FunctionScore.of(f -> f.filter(fl -> fl.match(m -> m.field("name").query(keyword))).weight(8.0)),
                        FunctionScore.of(f -> f.filter(fl -> fl.match(m -> m.field("subTitle").query(keyword))).weight(2.0)),
                        FunctionScore.of(f -> f.filter(fl -> fl.match(m -> m.field("keywords").query(keyword))).weight(2.0)),
                        FunctionScore.of(f -> f.filter(fl -> fl.term(t -> t.field("brandId").value(brandId))).weight(5.0)),
                        FunctionScore.of(f -> f.filter(fl -> fl.term(t -> t.field("productCategoryId").value(productCategoryId))).weight(3.0))
                )
                .scoreMode(FunctionScoreMode.Sum)
                .minScore(2.0)
        ));

        Query root = Query.of(q -> q.bool(b -> b
                .must(functionScore)
                .mustNot(mn -> mn.term(t -> t.field("id").value(id)))
        ));

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(root)
                .withPageable(pageable)
                .build();
        LOGGER.info("recommend query: {}", searchQuery.getQuery());

        SearchHits<EsProduct> searchHits = elasticsearchOperations.search(searchQuery, EsProduct.class);
        long total = searchHits.getTotalHits();
        if (total <= 0) {
            return new PageImpl<>(ListUtil.empty(), pageable, 0);
        }
        List<EsProduct> list = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public EsProductRelatedInfo searchRelatedInfo(String keyword) {
        Query query = StrUtil.isEmpty(keyword)
                ? Query.of(q -> q.matchAll(m -> m))
                : Query.of(q -> q.multiMatch(m -> m.query(keyword).fields("name", "subTitle", "keywords")));

        Aggregation attrValuesAgg = Aggregation.of(a4 -> a4.terms(t -> t.field("attrValueList.value").size(200)));
        Aggregation attrNamesAgg = Aggregation.of(a5 -> a5.terms(t -> t.field("attrValueList.name").size(10)));
        Aggregation attrIdsAgg = Aggregation.of(a3 -> a3
                .terms(t -> t.field("attrValueList.productAttributeId").size(200))
                .aggregations("attrValues", attrValuesAgg)
                .aggregations("attrNames", attrNamesAgg));
        Aggregation productAttrsAgg = Aggregation.of(a2 -> a2
                .filter(f -> f.term(t -> t.field("attrValueList.type").value(1)))
                .aggregations("attrIds", attrIdsAgg));
        Aggregation allAttrValuesAgg = Aggregation.of(a -> a
                .nested(n -> n.path("attrValueList"))
                .aggregations("productAttrs", productAttrsAgg));

        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(query)
                .withPageable(PageRequest.of(0, 0))
                .withAggregation("brandNames", Aggregation.of(a -> a.terms(t -> t.field("brandName").size(100))))
                .withAggregation("productCategoryNames", Aggregation.of(a -> a.terms(t -> t.field("productCategoryName").size(100))))
                .withAggregation("allAttrValues", allAttrValuesAgg)
                .build();

        SearchHits<EsProduct> searchHits = elasticsearchOperations.search(searchQuery, EsProduct.class);
        return convertProductRelatedInfo(searchHits);
    }

    private static EsProductRelatedInfo convertProductRelatedInfo(SearchHits<EsProduct> searchHits) {
        EsProductRelatedInfo productRelatedInfo = new EsProductRelatedInfo();
        if (!searchHits.hasAggregations()) {
            return productRelatedInfo;
        }
        ElasticsearchAggregations elAgg = (ElasticsearchAggregations) searchHits.getAggregations().aggregations();
        Map<String, Aggregate> aggregationMap = new HashMap<>();
        for (ElasticsearchAggregation ea : elAgg.aggregations()) {
            org.springframework.data.elasticsearch.client.elc.Aggregation springAgg = ea.aggregation();
            aggregationMap.put(springAgg.getName(), springAgg.getAggregate());
        }

        Aggregate brandNamesAgg = aggregationMap.get("brandNames");
        if (brandNamesAgg != null && brandNamesAgg.isSterms()) {
            List<String> brandNameList = brandNamesAgg.sterms().buckets().array().stream()
                    .map(b -> fieldValueToString(b.key()))
                    .collect(Collectors.toList());
            productRelatedInfo.setBrandNames(brandNameList);
        }

        Aggregate productCategoryNamesAgg = aggregationMap.get("productCategoryNames");
        if (productCategoryNamesAgg != null && productCategoryNamesAgg.isSterms()) {
            List<String> categoryNames = productCategoryNamesAgg.sterms().buckets().array().stream()
                    .map(b -> fieldValueToString(b.key()))
                    .collect(Collectors.toList());
            productRelatedInfo.setProductCategoryNames(categoryNames);
        }

        Aggregate allAttrValues = aggregationMap.get("allAttrValues");
        if (allAttrValues != null && allAttrValues.isNested()) {
            NestedAggregate nested = allAttrValues.nested();
            Aggregate productAttrsAgg = nested.aggregations().get("productAttrs");
            if (productAttrsAgg != null && productAttrsAgg.isFilter()) {
                FilterAggregate filter = productAttrsAgg.filter();
                Aggregate attrIdsAgg = filter.aggregations().get("attrIds");
                if (attrIdsAgg != null && attrIdsAgg.isLterms()) {
                    List<EsProductRelatedInfo.ProductAttr> attrList = new ArrayList<>();
                    for (LongTermsBucket attrId : attrIdsAgg.lterms().buckets().array()) {
                        EsProductRelatedInfo.ProductAttr attr = new EsProductRelatedInfo.ProductAttr();
                        attr.setAttrId(attrId.key());
                        Aggregate attrValuesAgg = attrId.aggregations().get("attrValues");
                        List<String> attrValueList = new ArrayList<>();
                        if (attrValuesAgg != null && attrValuesAgg.isSterms()) {
                            for (StringTermsBucket attrValue : attrValuesAgg.sterms().buckets().array()) {
                                attrValueList.add(fieldValueToString(attrValue.key()));
                            }
                        }
                        attr.setAttrValues(attrValueList);
                        Aggregate attrNamesAgg = attrId.aggregations().get("attrNames");
                        if (attrNamesAgg != null && attrNamesAgg.isSterms()
                                && !attrNamesAgg.sterms().buckets().array().isEmpty()) {
                            attr.setAttrName(fieldValueToString(attrNamesAgg.sterms().buckets().array().get(0).key()));
                        }
                        attrList.add(attr);
                    }
                    productRelatedInfo.setProductAttrs(attrList);
                }
            }
        }
        return productRelatedInfo;
    }

    private static String fieldValueToString(FieldValue v) {
        if (v.isString()) {
            return v.stringValue();
        }
        if (v.isLong()) {
            return Long.toString(v.longValue());
        }
        if (v.isDouble()) {
            return Double.toString(v.doubleValue());
        }
        return v._toJsonString();
    }
}
