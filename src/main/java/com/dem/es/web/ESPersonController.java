package com.dem.es.web;


import com.dem.es.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * es
 */
@RestController
@RequestMapping("/people")
@Api(value = "elasticserch 用户数据查询",description="elasticserch person/man 控制器")
public class ESPersonController {

    private static Log log = LogFactory.getLog(ESPersonController.class);
    @Autowired
    private TransportClient client;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取用户信息", notes = "")
    @GetMapping("/man/{id}")
    public ResponseEntity getById(@PathVariable String id) {
        if (StringUtils.hasLength(id)) {
            GetResponse response = client.prepareGet(Constant.INDEX_PEOPLE, Constant.TYPE_MAN, id).get();
            if (response.isExists()) {
                return new ResponseEntity(response.getSource(), HttpStatus.OK);
            }
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    /**
     * 新增
     *
     * @param name
     * @param country
     * @param age
     * @param date
     * @return
     */
    @ApiOperation(value = "新增用户信息,返回新建用户id (uuid)", notes = "", response = String.class)
    @PostMapping("/man/add")
    public ResponseEntity add(String name, String country, Integer age, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        try {
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("name", name)
                    .field("age", age)
                    .field("country", country)
                    .field("date", date.getTime())
                    .endObject();
            IndexResponse response = client.prepareIndex(Constant.INDEX_PEOPLE, Constant.TYPE_MAN).setSource(contentBuilder).get();
            return new ResponseEntity(response.getId(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "根据用户id删除,删除返回deleted", notes = "")
    @DeleteMapping("/man/delete/{id}")
    public ResponseEntity deleteById(@PathVariable String id) {
        try {
            DeleteResponse res = client.prepareDelete(Constant.INDEX_PEOPLE, Constant.TYPE_MAN, id).get();
            return new ResponseEntity(res.getResult().toString(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "根据用户id更新数据", notes = "")
    @PutMapping("/man/update/{id}")
    public ResponseEntity updateById(@PathVariable String id, String name, String country, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
//        UpdateRequest req = new UpdateRequest(Constant.INDEX_PEOPLE,Constant.TYPE_MAN,id);
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            if (StringUtils.hasLength(name)) {
                builder.field("name", name);
            }
            if (StringUtils.hasLength(country)) {
                builder.field("country", country);
            }
            if (null != date) {
                builder.field("date", date.getTime());
            }
            builder.endObject();
//            req.doc(builder);
            UpdateResponse response = client.prepareUpdate(Constant.INDEX_PEOPLE, Constant.TYPE_MAN, id).setDoc(builder).get();
            return new ResponseEntity(response.getResult().toString(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "模糊查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "country", value = "国家", dataType = "String"),
            @ApiImplicitParam(name = "minAge", value = "最小年龄", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "maxAge", value = "最大年级", required = false, dataType = "Integer")
    })
    @PostMapping("/man/_query")
    public ResponseEntity query(String name, String country, Integer minAge, Integer maxAge) {
        try {
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            if (StringUtils.hasLength(name)) {
                boolQuery.must(QueryBuilders.matchQuery("name", name));
            }
            if (StringUtils.hasLength(country)) {
                boolQuery.must(QueryBuilders.matchQuery("country", country));
            }
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
            if (minAge != null && minAge > 0) {
                rangeQuery.gte(minAge);
            }
            if (maxAge != null && maxAge > 0) {
                rangeQuery.lte(maxAge);
            }
            boolQuery.filter(rangeQuery);
            SearchRequestBuilder searchRequestBuilder = client.prepareSearch(Constant.INDEX_PEOPLE)
                    .setTypes(Constant.TYPE_MAN)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(boolQuery)
                    .setFrom(0)
                    .setSize(10);
            SearchResponse res = searchRequestBuilder.get();
            List<Map<String, Object>> list = new ArrayList<>();
            for (SearchHit hit : res.getHits()) {
                list.add(hit.getSource());
            }
            return new ResponseEntity(list, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
