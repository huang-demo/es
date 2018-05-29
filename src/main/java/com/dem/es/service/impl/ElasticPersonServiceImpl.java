package com.dem.es.service.impl;

import com.dem.es.domain.ElasticConstant;
import com.dem.es.domain.ElasticFieldTypeEnum;
import com.dem.es.domain.Person;
import com.dem.es.domain.req.ElasticReq;
import com.dem.es.domain.vo.ElasticMappingTypeVO;
import com.dem.es.domain.vo.ElasticNestMappingVO;
import com.dem.es.repository.PersonJpaReponsitory;
import com.dem.es.service.ElasticPersonService;
import com.dem.es.util.*;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
@Service
public class ElasticPersonServiceImpl extends ElasticBaseServiceImpl implements ElasticPersonService {


    private static final Logger log = Logger.getLogger(ElasticPersonServiceImpl.class);
    @Value("${elastic.person.shardNum}")
    private Integer shardNum;
    @Value("${elastic.person.replicaNum}")
    private Integer replicaNum;


    @Autowired
    private PersonJpaReponsitory personJpaReponsitory;

    @Override
    public void init() {
        try {
            //
            List<ElasticMappingTypeVO> list = new ArrayList<>();
            list.add(getPersonType());
            createIndexAndType(ElasticConstant.INDEX_PERESON, shardNum, replicaNum, list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private ElasticMappingTypeVO getPersonType() {
        ElasticMappingTypeVO vo = new ElasticMappingTypeVO();
        vo.setName(ElasticConstant.TYPE_PERSON_PERSONINFO);
        vo.setDynamic(false);
        vo.setFields(getPersonFieldMap());
        return vo;
    }

    /**
     * 字段映射
     */

    private Map<String, ElasticFieldTypeEnum> getPersonFieldMap() {
        Map<String, ElasticFieldTypeEnum> mapping = new HashMap<>();
        mapping.put("id", ElasticFieldTypeEnum.LONG);
        mapping.put("age", ElasticFieldTypeEnum.INTEGER);
        mapping.put("name", ElasticFieldTypeEnum.TEXT_IK_MAX_WORD);
        mapping.put("address", ElasticFieldTypeEnum.TEXT_IK_MAX_WORD);
        mapping.put("createTime", ElasticFieldTypeEnum.DATE);
        mapping.put("updateTime", ElasticFieldTypeEnum.DATE);
        mapping.put("otherInfo", ElasticFieldTypeEnum.NESTED);
        return mapping;
    }

    @Override
    protected Map<String, ElasticNestMappingVO> getNestMap() {
        Map<String, ElasticNestMappingVO> nestMap = new HashMap<>();
        ElasticNestMappingVO priceJsonNest = new ElasticNestMappingVO();
        Map<String, ElasticFieldTypeEnum> priceJsonFileds = new HashMap<>();
        priceJsonFileds.put("typeId", ElasticFieldTypeEnum.LONG);
        priceJsonFileds.put("typeName", ElasticFieldTypeEnum.KEYWORD);
        priceJsonFileds.put("totalCount", ElasticFieldTypeEnum.LONG);
        priceJsonFileds.put("avgPrice", ElasticFieldTypeEnum.DOUBLE);
        priceJsonFileds.put("minPrice", ElasticFieldTypeEnum.DOUBLE);
        priceJsonFileds.put("maxPrice", ElasticFieldTypeEnum.DOUBLE);
        priceJsonNest.setFields(priceJsonFileds);
        priceJsonNest.setName("otherInfo");
        nestMap.put("otherInfo", priceJsonNest);
        return nestMap;
    }

    @Override
    public void initData(Long start) {
        List<Person> personList = personJpaReponsitory.findAll();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Person person : personList) {
            list.add(ObjectUtil.obj2Map(person));
        }

        try {
            batchAdd(list, ElasticConstant.INDEX_PERESON,
                    ElasticConstant.TYPE_PERSON_PERSONINFO);

            list = null;

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return;
        }

    }
    /**
     * 字段下面fields
     */
    @Override
    protected Map<String, Map<String, ElasticFieldTypeEnum>> getFieldsMap() {
        Map<String, Map<String, ElasticFieldTypeEnum>> fieldsMap = new HashMap<>();
        Map<String, ElasticFieldTypeEnum> nameFields = new HashMap<>();
        nameFields.put("pinyin", ElasticFieldTypeEnum.TEXT_PINYIN);
        nameFields.put("keyword", ElasticFieldTypeEnum.KEYWORD);
        fieldsMap.put("name", nameFields);
        return fieldsMap;
    }


    @Override
    public PageBean search(ElasticReq req) {
        PageBean page = new PageBean();
        SearchRequestBuilder request = getSearchRequest(req.getPage(), req.getPageSize());
        request.setQuery(getQuery(req));
        SearchResponse response = request.get();
        SearchHits responseHits = response.getHits();
        page.setTotalNum(responseHits.totalHits);
        page.setCurrentPage(req.getPage());
        page.setPageSize(req.getPageSize());
        List<Map<String, Object>> item = new ArrayList<>();
        Map<String, Object> cur = null;
        for (SearchHit hit : responseHits.getHits()) {
            cur = hit.getSourceAsMap();
            item.add(cur);
        }
        page.setItems(item);
        return page;
    }

    @Override
    protected SearchRequestBuilder getSearchRequest(int page, int size) {
        return super.getSearchRequest(page, size)
                .setIndices(ElasticConstant.INDEX_PERESON)
                .setTypes(ElasticConstant.TYPE_PERSON_PERSONINFO);
    }

    /**
     * 创建搜索请求
     */
    private QueryBuilder getQuery(ElasticReq req) {
        boolean flag = true;
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (StringUtil.hasLength(req.getKw())) {
            flag = false;
            QueryStringQueryBuilder queryStrBuild = QueryBuilders.queryStringQuery(req.getKw())
                    .analyzer(ElasticConstant.ANALYZER_IK_SMART).field("name");

            boolQuery.should(queryStrBuild);
        }
        if (flag) {
            //没有搜索条件,查询全部
            return QueryBuilders.matchAllQuery();
        }
        return boolQuery;
    }
}
