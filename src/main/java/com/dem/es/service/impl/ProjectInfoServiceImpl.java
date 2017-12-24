package com.dem.es.service.impl;

import com.dem.es.domain.ProjectInfo;
import com.dem.es.repository.ProjectInfoJpaResponsitory;
import com.dem.es.service.ProjectInfoService;
import com.dem.es.util.Constant;
import com.dem.es.util.PageBean;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {

    @Autowired
    private ProjectInfoJpaResponsitory projectInfoJpaResponsitory;

    @Autowired
    private TransportClient transportClient;


    @Override
    public void batchAdd() {
        List<ProjectInfo> list = projectInfoJpaResponsitory.findAll();
        BulkResponse bulkResponse = null;
        long start = System.currentTimeMillis();
        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
        try {
            int index = 0;
            for (ProjectInfo projectInfo : list) {
                index++;
                bulkRequest.add(transportClient.prepareIndex(Constant.ELASTIC_INDEX_PROJECT, Constant.ELASTIC_TYPES_PROJECTBASEINFO)
                        .setSource(jsonBuilder()
                                .startObject()
                                .field("id", projectInfo.getId())
                                .field("name", projectInfo.getName())
                                .field("content", projectInfo.getContent())
                                .field("pid", projectInfo.getPid())
                                .field("createTime", projectInfo.getCreateTime().getTime())
                                .field("updateTime", projectInfo.getUpdateTime().getTime())
                                .field("projectType", projectInfo.getProjectType())
                                .endObject()
                        )
                );
                if (index % 1000 == 0) {
                    bulkResponse = bulkRequest.execute().actionGet();
                    if (bulkResponse.hasFailures()) {
                        for (BulkItemResponse item : bulkResponse.getItems()) {
                            System.out.println(item.getFailureMessage());
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        bulkResponse = bulkRequest.execute().actionGet();
        System.out.println("耗时:" + (System.currentTimeMillis() - start));
        if (bulkResponse.hasFailures()) {
            for (BulkItemResponse item : bulkResponse.getItems()) {
                System.out.println(item.getFailureMessage());
            }
        }

    }

    @Override
    public String addOne(Long projectId) throws IOException {
        ProjectInfo projectInfo = projectInfoJpaResponsitory.findOne(projectId);
        if (projectInfo == null) {
            return "";
        }
        IndexRequestBuilder builder = transportClient.prepareIndex(Constant.ELASTIC_INDEX_PROJECT_TEST, Constant.ELASTIC_TYPES_PROJECTINFO);
        XContentBuilder contentBuilder = XContentFactory.jsonBuilder()
                .startObject()
                .field("id", projectInfo.getId())
                .field("name", projectInfo.getName())
                .field("amount", projectInfo.getAmount())
                .field("pid", projectInfo.getPid())
                .field("content", projectInfo.getContent())
                .field("createTime", projectInfo.getCreateTime().getTime())
                .field("projectType", projectInfo.getProjectType())
                .field("updateTime", projectInfo.getUpdateTime())
                .endObject();
        IndexResponse response = builder.setSource(contentBuilder).get();
        return response.getId();
    }

    @Override
    public PageBean search(String kw, int page, int pageSize) {

        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(Constant.ELASTIC_INDEX_PROJECT);
        searchRequestBuilder.setTypes(Constant.ELASTIC_TYPES_PROJECTINFO);
        /*SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(Constant.ELASTIC_INDEX_PROJECT_TEST);
        searchRequestBuilder.setTypes(Constant.ELASTIC_TYPES_PROJECTINFO);*/
        searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(kw);
        queryBuilder.analyzer("ik_max_word");
        queryBuilder.field("name");
        searchRequestBuilder.setQuery(queryBuilder);
        // 分页应用
        searchRequestBuilder.setFrom((page - 1) * pageSize).setSize(pageSize);
        HighlightBuilder hiBuilder = new HighlightBuilder();
        hiBuilder.preTags("<span style=\"color:'red'\">")
                .postTags("</span>");
        hiBuilder.field("name")
                .field("content");
        searchRequestBuilder.highlighter(hiBuilder);
        // 执行搜索,返回搜索响应信息
        SearchResponse response = searchRequestBuilder.execute()
                .actionGet();
        SearchHits hits = response.getHits();
        hits.getHits();
        PageBean resultPage = new PageBean();
        resultPage.setCurrentPage(page);
        resultPage.setPageSize(pageSize);
        resultPage.setTotalNum(hits.totalHits);
        resultPage.setItems(getPageResult(hits));
        return resultPage;
    }

    @Override
    public PageBean searchProjectName(String kw) {
        SearchRequestBuilder search = transportClient.prepareSearch(Constant.ELASTIC_INDEX_PROJECT_TEST);
        search.setTypes(Constant.ELASTIC_TYPES_PROJECTINFO);
        QueryStringQueryBuilder query = new QueryStringQueryBuilder(kw);
        query.analyzer("ik_max_word");
        query.field("name");
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("agg")
                .field("name")
                .subAggregation(AggregationBuilders.topHits("top").explain(true).size(1).from(10));
        search.setFetchSource("name", "");
//        AggregationBuilder agg =new FilterAggregationBuilder();
//        search.addAggregation(agg);
//        search.fields("projectName");
        search.setQuery(query);
        SearchResponse response = search.execute().actionGet();
        PageBean page = new PageBean();
        List<Object> items = new ArrayList<>();
        for (SearchHit searchHitFields : response.getHits()) {
            items.add(searchHitFields.getSourceAsMap());
//            items.add(searchHitFields.getSource());
        }
        page.setItems(items);
        return page;
    }

    private List<Map<String, Object>> getPageResult(SearchHits hits) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit searchHitFields : hits.getHits()) {
            list.add(getHightLightObj(searchHitFields));
        }
        return list;
    }

    private Map<String, Object> getHightLightObj(SearchHit searchHitFields) {
        StringBuilder builder;
        Text[] fragments;
//        Map<String, Object> source = searchHitFields.getSource();
        Map<String, Object> source = searchHitFields.getSourceAsMap();
        Map<String, HighlightField> highlightFields = searchHitFields.getHighlightFields();
        for (String s : highlightFields.keySet()) {
            builder = new StringBuilder(30);
            HighlightField field = highlightFields.get(s);
            fragments = field.fragments();
            for (Text fragment : fragments) {
                builder.append(fragment);
            }
            source.put(s, builder.toString());
        }
        return source;
    }


    @Override
    public int deleteByProjectName(String projectName) {
        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
        SearchResponse response = transportClient.prepareSearch(Constant.ELASTIC_INDEX_PROJECT_TEST)
                .setTypes(Constant.ELASTIC_TYPES_PROJECTINFO)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(termQuery("name", projectName))
                .setFrom(0).setSize(20).setExplain(true).execute().actionGet();
        for (SearchHit hit : response.getHits()) {
            String id = hit.getId();
            bulkRequest.add(transportClient.prepareDelete("project", "projectInfo", id).request());
        }
        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            for (BulkItemResponse item : bulkResponse.getItems()) {
                System.out.println(item.getFailureMessage());
            }
        } else {
            System.out.println("delete ok");
        }
        return 0;
    }

    @Override
    public Object queryMutiType(String kw, int page, int pageSize) {
        PageBean result = new PageBean();
        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(kw);
        queryBuilder.analyzer("ik_max_word");
        queryBuilder.field("name").field("content");
        SearchRequestBuilder srb1 = transportClient.prepareSearch(Constant.ELASTIC_INDEX_PROJECT_TEST)
                .setTypes(Constant.ELASTIC_TYPES_PROJECTINFO)
                .setQuery(queryBuilder);

        SearchRequestBuilder srb2 = transportClient
                .prepareSearch("people").setTypes("man")
                .setQuery(QueryBuilders.matchQuery("name", kw));

        MultiSearchResponse sr = transportClient.prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .get();
        long nbHits = 0;
        List<Object> pageItem = new ArrayList<>();
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            SearchHits hits = response.getHits();
            nbHits += hits.getTotalHits();
            for (SearchHit hit : hits.getHits()) {
                pageItem.add(hit);
            }
        }
        result.setTotalNum(nbHits);
        result.setItems(pageItem);
        return result;
    }

    public Object queryMutiIndex(String kw, int page, int pageSize) {

        return null;
    }

    @Override
    public List<ProjectInfo> getAll() {
        return projectInfoJpaResponsitory.findAll();
    }

}
