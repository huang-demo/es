package com.dem.es.service.impl;

import com.dem.es.domain.ElasticConstant;
import com.dem.es.domain.ElasticFieldTypeEnum;
import com.dem.es.domain.req.ElasticReq;
import com.dem.es.domain.vo.ElasticMappingTypeVO;
import com.dem.es.domain.vo.ElasticNestMappingVO;
import com.dem.es.service.ElasticBaseService;
import com.dem.es.util.*;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class ElasticBaseServiceImpl implements ElasticBaseService {


    @Autowired
    private TransportClient transportClient;

    @Override
    public void init() {

    }

    @Override
    public boolean delIndex(String indexName) {
        if (!isExistsIndex(indexName)) {
            return true;
        }
        DeleteIndexResponse dResponse = transportClient.admin().indices().prepareDelete(indexName)
                .execute().actionGet();
        return dResponse.isAcknowledged();
    }


    @Override
    public boolean createIndex(String index, int shardNum, int replicaNum) {
        if (isExistsIndex(index)) {
            return true;
        }
        CreateIndexRequestBuilder prepareCreate = transportClient.admin().indices().prepareCreate(index);
        Map<String, Integer> map = new HashMap<>();
        map.put("number_of_shards", shardNum);
        map.put("number_of_replicas", replicaNum);

        prepareCreate.setSettings(map);
        CreateIndexResponse response = prepareCreate.execute().actionGet();
        return response.isAcknowledged();
    }

    @Override
    public boolean isExistsIndex(String indexName) {
        IndicesExistsResponse response =
                transportClient.admin().indices().exists(
                        new IndicesExistsRequest().indices(new String[]{indexName})).actionGet();
        return response.isExists();
    }

    @Override
    public boolean createMapping(String indexName, String typeName, Map<String, ElasticFieldTypeEnum> fields) {
        boolean existsType = isExistsType(indexName, typeName);
        if (existsType) {
            return true;
        }
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder()
                    .startObject()
                    .field("dynamic", false)
                    //strict
                    .startObject("properties");
            for (String fieldName : fields.keySet()) {
                setMapping(fieldName, fields.get(fieldName), mapping, this.getFieldsMap().get(fieldName));
            }
            mapping.endObject()
                    .endObject();
            PutMappingRequest request = Requests.putMappingRequest(indexName).type(typeName).source(mapping);
            PutMappingResponse response = transportClient.admin().indices().putMapping(request).actionGet();
            return response.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置字段属性
     *
     * @param fieldName
     * @param fieldType
     * @param mapping
     * @param fields
     * @throws IOException
     */
    private void setMapping(String fieldName, ElasticFieldTypeEnum fieldType, XContentBuilder mapping,
                            Map<String, ElasticFieldTypeEnum> fields) throws IOException {
        if (StringUtil.isEmpty(fieldName)) {
            return;
        }
        mapping.startObject(fieldName);
        switch (fieldType) {
            case BOOLEAN:
                mapping.field("type", "boolean");
                break;
            case SHORT:
                mapping.field("type", "short")
                        .field("index", ElasticConstant.ANALYZER_NOT_ANALYZED);
                break;
            case INTEGER:
                mapping.field("type", "integer")
                        .field("index", ElasticConstant.ANALYZER_NOT_ANALYZED);
                break;
            case DOUBLE:
                mapping.field("type", "double");
                break;
            case LONG:
                mapping.field("type", "long");
                break;
            case KEYWORD:
                mapping.field("type", "keyword");
                addFields(fields, mapping);
                break;
            case TEXT_NO_ANALYZED:
                mapping.field("type", "text")
                        .field("fielddata", true)
                        .field("index", ElasticConstant.ANALYZER_NOT_ANALYZED);
                addFields(fields, mapping);
                break;
            case TEXT_IK_SMART:
                mapping.field("type", "text")
                        .field("fielddata", true)
                        .field("analyzer", ElasticConstant.ANALYZER_IK_SMART)
                        .field("search_analyzer", ElasticConstant.ANALYZER_IK_SMART);
                addFields(fields, mapping);
                break;
            case TEXT_IK_MAX_WORD:
                mapping.field("type", "text")
                        .field("fielddata", true)
                        .field("analyzer", ElasticConstant.ANALYZER_IK_MAX_WORD)
                        .field("search_analyzer", ElasticConstant.ANALYZER_IK_SMART);
                addFields(fields, mapping);
                break;
            case TEXT_PINYIN:
                mapping.field("type", "text")
                        .field("analyzer", ElasticConstant.ANALYZER_PINYIN);
                addFields(fields, mapping);
                break;
            case IP:
                mapping.field("type", "ip");
                addFields(fields, mapping);
                break;
            case DATE:
                mapping.field("type", "date")
                        .field("index", ElasticConstant.ANALYZER_NOT_ANALYZED)
                        .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd'T'HH:mm:ss.SSSZ||yyyy-MM-dd||epoch_millis");
                break;
            case NESTED:
                mapping.field("type", "nested");
                mapping.startObject("properties");
                Map<String, ElasticNestMappingVO> nestMap = getNestMap();
                ElasticNestMappingVO elasticNestMappingVO = nestMap.get(fieldName);
                if (elasticNestMappingVO != null) {
                    for (Map.Entry<String, ElasticFieldTypeEnum> entry : elasticNestMappingVO.getFields().entrySet()) {
                        setMapping(entry.getKey(), entry.getValue(), mapping, null);
                    }
                }
                mapping.endObject();//properties
                break;
            default:
                mapping.field("type", "text")
                        .field("index", ElasticConstant.ANALYZER_NOT_ANALYZED);
                addFields(fields, mapping);
                break;
        }
        mapping.endObject();
    }

    //子类自己实现
    protected Map<String, ElasticNestMappingVO> getNestMap() {
        return Collections.EMPTY_MAP;
    }

    /**
     * 对应字段需要而外添加的类型
     *
     * @return
     */
    protected Map<String, Map<String, ElasticFieldTypeEnum>> getFieldsMap() {
        return Collections.EMPTY_MAP;
    }

    /**
     * 添加 字段里面fields 内容
     *
     * @param map
     * @param mapping
     * @throws IOException
     */
    private void addFields(Map<String, ElasticFieldTypeEnum> map, XContentBuilder mapping) throws IOException {
        if (map != null && map.size() > 0) {
            mapping.startObject("fields");
            for (String fileName : map.keySet()) {
                setMapping(fileName, map.get(fileName), mapping, Collections.EMPTY_MAP);
            }
            mapping.endObject();
        }

    }

    /**
     * 创建索引和类型-多个类型的用这个
     */
    protected boolean createIndexAndType(String index, int shardNum, int replicaNum, List<ElasticMappingTypeVO> list) throws Exception {
        if (isExistsIndex(index)) {
            return true;
        }
        CreateIndexRequestBuilder prepareCreate = transportClient.admin().indices().prepareCreate(index);
        Map<String, Integer> map = new HashMap<>();
        map.put("number_of_shards", shardNum);
        map.put("number_of_replicas", replicaNum);
        prepareCreate.setSettings(map);
        for (ElasticMappingTypeVO vo : list) {
            XContentBuilder mapping = jsonBuilder()
                    .startObject();//1
            mapping.field("dynamic", vo.getDynamic());
            if (vo.getRouting() != null) {
                mapping.startObject("_routing")
                        .field("required", vo.getRouting())
                        .endObject();//3
            }
            if (StringUtil.hasLength(vo.getParent())) {
                mapping.startObject("_parent")
                        .field("type", vo.getParent())
                        .endObject();//3
            }
            mapping.startObject("properties");//2
            for (Map.Entry<String, ElasticFieldTypeEnum> fieldEnt : vo.getFields().entrySet()) {
                setMapping(fieldEnt.getKey(), fieldEnt.getValue(), mapping, this.getFieldsMap().get(fieldEnt.getKey()));
            }
            mapping.endObject()//2
                    .endObject();//1
            prepareCreate.addMapping(vo.getName(), mapping);
        }
        CreateIndexResponse response = prepareCreate.execute().actionGet();
        return response.isAcknowledged();
    }


    @Override
    public boolean isExistsType(String indexName, String indexType) {
        TypesExistsResponse response =
                transportClient.admin().indices()
                        .typesExists(new TypesExistsRequest(new String[]{indexName}, indexType)
                        ).actionGet();
        return response.isExists();
    }

    @Override
    public Integer batchAdd(List<Map<String, Object>> list, String index, String type) throws IOException {
        //本次导入的最大id
        int maxId = 0;
        if (list.size() == 0) {
            return maxId;
        }
        //一万条导入一次
        int maxCount = 10000;

        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
        //导入失败统计
        List<BulkItemResponse> failItems = new ArrayList<>();
        BulkResponse bulkResponse = null;
        int cur = 0;
        Integer id = null;
        String idStr = "";
        IndexRequestBuilder requestBuilder = null;
        for (Map<String, Object> objMap : list) {
            cur++;
            requestBuilder = transportClient.prepareIndex(index, type);
            if (!StringUtil.isEmpty(objMap.get("id"))) {
                idStr = objMap.get("id").toString();
                id = Integer.valueOf(idStr);
                maxId = maxId < id ? id : maxId;
                requestBuilder.setId(idStr);
            }
            requestBuilder.setSource(getSourceBuilder(objMap));
            //指标属性设置父集
            setParent(requestBuilder, objMap, type);

            bulkRequest.add(requestBuilder);

            if (cur % maxCount == 0) {
                bulkResponse = bulkRequest.execute().actionGet();

                if (bulkResponse.hasFailures()) {
                    failItems.addAll(Arrays.asList(bulkResponse.getItems()));
                }
            }
        }
        bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            failItems.addAll(Arrays.asList(bulkResponse.getItems()));
        }
        maxId += 1;
        return maxId;
    }

    /**
     * 维护父级层级关系
     */
    private void setParent(IndexRequestBuilder requestBuilder, Map<String, Object> objMap, String type) {

    }

    /**
     * 留给子类具体实现
     *
     * @param
     * @return
     * @throws IOException
     */
    protected XContentBuilder getSourceBuilder(Map<String, Object> objMap) throws IOException {
        XContentBuilder xContentBuilder = jsonBuilder().startObject();
        String key = "";
        for (Map.Entry<String, Object> entry : objMap.entrySet()) {
            key = entry.getKey();
            if (StringUtil.isEmpty(key)) {
                continue;
            }
            //日期类型单独处理
            if (isDateField(key)) {
                Date curDate = (Date) entry.getValue();
                xContentBuilder.field(entry.getKey(), DateUtil.formatDateToString(curDate, "yyyy-MM-dd"));
                continue;
            } else {
                xContentBuilder.field(key, entry.getValue());
            }

        }
        xContentBuilder.endObject();
        return xContentBuilder;
    }

    /**
     * 简单根据字段名称判断是否为日期类型
     *
     * @param key
     * @return
     */
    private boolean isDateField(String key) {
        if (StringUtil.isEmpty(key)) {
            return false;
        }
        return key.endsWith("Date")
                || key.endsWith("date")
                || key.endsWith("Time");
    }

    @Override
    public PageBean searchByKw(ElasticReq req) {

        String[] indexNames = {ElasticConstant.INDEX_PERESON};
        String[] fields = {"name"};
        SearchRequestBuilder searchRequestBuilder = this.getSearchRequest(req.getPage(), req.getPageSize())
                .setIndices(indexNames)
                .setTypes(fields)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(req.getKw());
        queryBuilder.analyzer(ElasticConstant.ANALYZER_IK_SMART);
        for (String field : fields) {
            queryBuilder.field(field);
        }
        searchRequestBuilder.setQuery(queryBuilder);

        /**
         *
         */
        addFieldsHighLight(searchRequestBuilder, fields);

        // 执行搜索,返回搜索响应信息
        SearchResponse response = searchRequestBuilder.execute()
                .actionGet();

        PageBean pageResult = new PageBean();
        SearchHits hits = response.getHits();
        pageResult.setTotalNum(hits.getTotalHits());
        pageResult.setCurrentPage(req.getPage());
        pageResult.setPageSize(req.getPageSize());
        pageResult.setItems(getPageResult(hits));
        return pageResult;
    }


    /**
     * 给字段添加高亮
     *
     * @param searchRequestBuilder
     */
    protected void addFieldsHighLight(SearchRequestBuilder searchRequestBuilder, String... fields) {
        HighlightBuilder hiBuilder = new HighlightBuilder();
        hiBuilder.preTags("<span class=\"" + ElasticConstant.DEFAULT_TEXT_CLASS + "\">");
        hiBuilder.postTags("</span>");
        for (String field : fields) {
            hiBuilder.field(field);
        }
        searchRequestBuilder.highlighter(hiBuilder);
    }

    /**
     * @param size
     * @return
     */
    protected SearchRequestBuilder getSearchRequest(int page, int size) {
        int start = (page - 1) * size;
        return transportClient.prepareSearch()
                .setFrom(start > 0 ? start : 0).setSize(size);
    }

    /**
     * 获取查询结果集
     *
     * @param hits
     * @return
     */
    protected List<Map<String, Object>> getPageResult(SearchHits hits) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit searchHitFields : hits.getHits()) {
            list.add(getHightLightObj(searchHitFields, true));
        }
        return list;
    }

    /**
     * 替换高亮字段
     *
     * @param searchHit
     * @return
     */
    protected Map<String, Object> getHightLightObj(SearchHit searchHit, boolean setAttrInfo) {
        StringBuilder builder;

        Text[] fragments;
        Map<String, Object> source = searchHit.getSourceAsMap();
        source.put("index", searchHit.getIndex());
        source.put("type", searchHit.getType());
        Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
        for (String s : highlightFields.keySet()) {
            builder = new StringBuilder(30);
            HighlightField field = highlightFields.get(s);
            fragments = field.fragments();
            for (Text fragment : fragments) {
                builder.append(fragment);
            }
            source.put(s, builder.toString());
        }
        if (setAttrInfo) {
            //设置其他属性
            setAttrInfo(source);
        }
        return source;
    }

    /**
     * 额外设置其他属性
     *
     * @param source
     */
    private void setAttrInfo(Map<String, Object> source) {


    }

    /**
     * -1倒叙 1正序
     *
     * @param type
     * @return
     */
    protected SortOrder getSortModel(Integer type) {
        switch (type) {
            case -1:
                return SortOrder.DESC;
            case 1:
                return SortOrder.ASC;
            default:
                return null;
        }
    }

}
