package com.dem.es.domain.vo;

import com.dem.es.domain.ElasticFieldTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class ElasticNestMappingVO implements Serializable {
    private String name;
    private Map<String, ElasticFieldTypeEnum> fields = new HashMap<>();


}
