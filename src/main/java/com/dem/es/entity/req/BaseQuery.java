package com.dem.es.entity.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseQuery implements Serializable{

    private Integer limit;
    private Integer page;

    {
        page = 1;
        limit = 10;
    }

    public int getOffset(){
        int start = (page - 1) * limit;
        return start > 0?start:0;
    }

    public int getLimit(){
        return limit > 0?limit:10;
    }
}
