package com.dem.es.controller;

import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private TransportClient client;

    /**
     * 根据id获取
     * @param id
     * @return
     */
    @RequestMapping("/man/{id}")
    public ResponseEntity getById(@PathVariable String id){
        if(StringUtils.hasLength(id)){
            GetResponse response = client.prepareGet("people", "man", id).get();
            if(response.isExists()){
                return new ResponseEntity(response.getSource(), HttpStatus.OK);
            }
        }
        return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }
}
