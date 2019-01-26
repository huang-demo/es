package com.dem.es.msg.sender.impl;

import com.dem.es.domain.constant.ElasticConstant;
import com.dem.es.msg.sender.IElasticPersonSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ElasticPersonSender extends ElasticBaseMsgSenderImpl implements IElasticPersonSender {
    private static final Logger logger = LoggerFactory.getLogger(ElasticPersonSender.class);


    protected String getIndex() {
        return ElasticConstant.INDEX_PERESON;
    }

    protected String getType() {
        return ElasticConstant.TYPE_PERSON_PERSONINFO;
    }



}
