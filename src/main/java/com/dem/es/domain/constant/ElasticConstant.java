package com.dem.es.domain.constant;

import java.io.Serializable;

public class ElasticConstant implements Serializable {

    /**
     * 索引名
     */

    public static final String INDEX_PERESON = "person";


    /**
     * 分类名称
     */
    public static final String TYPE_PERSON_PERSONINFO = "personInfo";

    /**
     * 粗粒度分析:中华人民共和国国旗==> 中华人民共和国, 国旗
     */
    public static final String ANALYZER_IK_SMART = "ik_smart";
    /**
     * 精粒度分析:中华人民共和国国旗==> 中华人民共和国,中华,人民, 国旗.....
     */
    public static final String ANALYZER_IK_MAX_WORD = "ik_max_word";
    public static final String ANALYZER_PINYIN = "pinyin";
    /**
     * 不分析
     */
    public static final String ANALYZER_NOT_ANALYZED = "not_analyzed";

    /**
     * 高亮标签样式
     */
    public static final String HIGHLIGHT_PRETAGS = "<span class=\'red-text\'>";
    public static final String HIGHLIGHT_POSTTAGS = "</span>";

    public static final String DEFAULT_TEXT_CLASS = "red-text";
    /**
     *
     */
    public static final String SUGGEST_NAME = "agg_suggest";


    public static final String ADD="ADD";
    public static final String UPDATE="UPDATE";
    public static final String DEL="DEL";

}
