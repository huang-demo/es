package com.dem.es.service.impl;

import com.dem.es.domain.ProjectInfo;
import com.dem.es.repository.ProjectInfoJpaResponsitory;
import com.dem.es.service.ProjectInfoService;
import com.dem.es.util.PageBean;
import com.google.gson.Gson;
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
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.boostingQuery;
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
        long start = System.currentTimeMillis();
        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
        try {
            for (ProjectInfo projectInfo : list) {
                bulkRequest.add(transportClient.prepareIndex("project", "projectInfo")
                        .setSource(jsonBuilder()
                                .startObject()
                                .field("projectId", projectInfo.getProjectId())
                                .field("projectInfo", projectInfo.getProjectId())
                                .field("projectName", projectInfo.getProjectName())
                                .field("content", projectInfo.getContent())
                                .field("pid", projectInfo.getPid())
                                .field("phone", projectInfo.getPhone())
                                .field("user", projectInfo.getUser())
                                .field("projectType", projectInfo.getProjectType())
                                .endObject()
                        )
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        BulkResponse bulkResponse = bulkRequest.get();
        Gson gson = new Gson();
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
        IndexRequestBuilder builder = transportClient.prepareIndex("project", "projectInfo");
        XContentBuilder contentBuilder = XContentFactory.jsonBuilder()
                .startObject()
                .field("projectId", projectInfo.getProjectId())
                .field("projectName", projectInfo.getProjectName())
                .field("content", projectInfo.getContent())
                .field("phone", projectInfo.getPhone())
                .field("pid", projectInfo.getPid())
                .field("user", projectInfo.getUser())
                .field("createDate", projectInfo.getCreateDate().getTime())
                .field("projectType", projectInfo.getProjectType())
                .endObject();
        IndexResponse response = builder.setSource(contentBuilder).get();
        return response.getId();
    }

    @Override
    public Object query(String kw, int page, int pageSize) {

        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch("project");
        searchRequestBuilder.setTypes("projectInfo");
        searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(kw);
        queryBuilder.analyzer("ik_smart");
        queryBuilder.field("projectName").field("content");
        searchRequestBuilder.setQuery(queryBuilder);
        // 分页应用
        searchRequestBuilder.setFrom((page - 1) * pageSize).setSize(pageSize);
        HighlightBuilder hiBuilder = new HighlightBuilder();
        hiBuilder.preTags("<span style=\"color:'red'\">");
        hiBuilder.postTags("</span>");
        hiBuilder.field("content");
        hiBuilder.field("projectName");
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
        Map<String, Object> source = searchHitFields.getSource();
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
        SearchResponse response = transportClient.prepareSearch("project").setTypes("projectInfo")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(termQuery("projectName", projectName))
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

        QueryBuilder qb = boostingQuery(
                termQuery("projectName", kw),
                termQuery("content", kw))
                .negativeBoost(0.2f);
        SearchRequestBuilder srb1 = transportClient.prepareSearch("project")
                .setTypes("projectInfo")
                .setQuery(QueryBuilders.queryStringQuery("elasticsearch")).setSize(1);
        SearchRequestBuilder srb2 = transportClient
                .prepareSearch("people").setTypes("man")
                .setQuery(QueryBuilders.matchQuery("name", kw)).setSize(1);

        MultiSearchResponse sr = transportClient.prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .get();
        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().getTotalHits();
        }
        return null;
    }

    public Object queryMutiIndex(String kw, int page, int pageSize) {

        return null;
    }

    @Override
    public List<ProjectInfo> getAll() {
        return projectInfoJpaResponsitory.findAll();
    }

}
