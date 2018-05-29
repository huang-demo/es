package com.dem.es.domain;

public enum ElasticFieldTypeEnum {
    BOOLEAN,
    SHORT,
    INTEGER,
    DOUBLE,
    LONG,
    KEYWORD,
    TEXT_NO_ANALYZED,
    //粗匹配
    TEXT_IK_SMART,
    //精匹配
    TEXT_IK_MAX_WORD,
    TEXT_PINYIN,
    DATE,
    IP,
    OBJECT,
    COMPLETION,
    //数组对象
    NESTED;
}
