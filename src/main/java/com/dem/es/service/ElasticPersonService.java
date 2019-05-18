package com.dem.es.service;

import com.dem.es.entity.req.ElasticReq;
import com.dem.es.util.PageBean;

import java.io.IOException;

public interface ElasticPersonService extends ElasticBaseService {

    /**
     * @param start
     */
    void initData(Long start);

    PageBean search(ElasticReq req);

    void update(Long id) throws IOException;

    void delete(Long id) throws IOException;
}
