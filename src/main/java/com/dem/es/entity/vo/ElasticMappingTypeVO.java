package com.dem.es.entity.vo;

import com.dem.es.entity.constant.ElasticFieldTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ElasticMappingTypeVO implements Serializable {
    private String name;
    private String parent;
    private Boolean routing;
    private Boolean dynamic;
    private Map<String, ElasticFieldTypeEnum> fields = new HashMap<>();
}
