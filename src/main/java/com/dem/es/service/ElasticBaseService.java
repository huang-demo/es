package com.dem.es.service;

import com.dem.es.entity.req.ElasticReq;
import com.dem.es.entity.constant.ElasticFieldTypeEnum;
import com.dem.es.util.PageBean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ElasticBaseService {
    /**
     * 初始化 索引 类型
     */
    void init();

    /**
     * 删除单个索引
     *
     * @param indexName
     * @return
     */
    boolean delIndex(String indexName);

    /**
     * 通过关键字搜索
     * @param req
     * @return
     */
    PageBean searchByKw(ElasticReq req);

    /**
     * 创建索引
     *
     * @param index
     * @param shardNum   主分片
     * @param replicaNum 复制分片
     * @return
     */
    boolean createIndex(String index, int shardNum, int replicaNum);


    /**
     * @param indexName
     * @return
     */
    boolean isExistsIndex(String indexName);


    /**
     * @param indexName
     * @param typeName
     * @param fields
     * @return
     */
    boolean createMapping(String indexName, String typeName, Map<String, ElasticFieldTypeEnum> fields);



    /**
     * @param indexName
     * @param indexType
     * @return
     */
    boolean isExistsType(String indexName, String indexType);





    /**
     * 批量添加 返回本次新增的最大id
     *
     * @param list
     * @param index
     * @param type
     * @return
     */
    Long batchAdd(List<Map<String, Object>> list, String index, String type) throws IOException;

    /**
     *
     * @param obj
     * @param index
     * @param type
     * @return
     * @throws IOException
     */
    Integer saveOrUpdate(Map<String,Object> obj,String index,String type) throws IOException;

    /**
     *
     * @param id
     * @param index
     * @param type
     * @return
     * @throws IOException
     */
    Integer deleteById(String id,String index,String type)throws IOException;
}
