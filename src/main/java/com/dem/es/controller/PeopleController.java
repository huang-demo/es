package com.dem.es.controller;

import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private TransportClient client;

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    @GetMapping("/man/{id}")
    public ResponseEntity getById(@PathVariable String id) {
        if (StringUtils.hasLength(id)) {
            GetResponse response = client.prepareGet("people", "man", id).get();
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
            IndexResponse response = client.prepareIndex("people", "man").setSource(contentBuilder).get();
            return new ResponseEntity(response.getId(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
