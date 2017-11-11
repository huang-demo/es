package com.dem.es.controller;


import com.dem.es.util.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
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
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private static Log log = LogFactory.getLog(PeopleController.class);
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
    @PutMapping("/man/update/{id}")
    public ResponseEntity updateById(@PathVariable String id,String name,String country,@DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
//        UpdateRequest req = new UpdateRequest(Constant.INDEX_PEOPLE,Constant.TYPE_MAN,id);
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            if(StringUtils.hasLength(name)){
                builder.field("name",name);
            }
            if(StringUtils.hasLength(country)){
                builder.field("country",country);
            }
            if(null!=date){
                builder.field("date",date.getTime());
            }
            builder.endObject();
//            req.doc(builder);
            UpdateResponse response = client.prepareUpdate(Constant.INDEX_PEOPLE, Constant.TYPE_MAN, id).setDoc(builder).get();
            return new ResponseEntity(response.getResult().toString(),HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
