package com.dem.es.domain.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class BaseQuery implements Serializable {

    private Integer pageSize = 10;
    private Integer page = 1;

    public int getOffset() {
        int start = (page - 1) * pageSize;
        return start > 0 ? start : 0;
    }

    public int getLimit() {
        return pageSize > 0 ? pageSize : 10;
    }
}
