package com.dem.es.domain.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ElasticReq extends BaseQuery {
    private String kw;
}
