package com.dem.es.service;

import com.dem.es.domain.req.ElasticReq;
import com.dem.es.util.PageBean;

public interface ElasticPersonService extends ElasticBaseService {

    /**
     * @param start
     */
    void initData(Long start);

    PageBean search(ElasticReq req);
}
