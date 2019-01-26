package com.dem.es.task;

import com.dem.es.domain.constant.ElasticConstant;
import com.dem.es.domain.constant.RedisConstant;
import com.dem.es.service.ElasticPersonService;
import com.dem.es.service.JedisClient;
import com.dem.es.util.DateUtil;
import com.dem.es.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * elstic person 增量同步
 */
@Component
public class ElasticPersonInitDataTask {
    private static final Logger logger = LoggerFactory.getLogger(ElasticPersonInitDataTask.class);
    @Autowired
    private ElasticPersonService elasticPersonService;
    @Autowired
    private JedisClient jedisClient;

    @Scheduled(cron = "0 */1 * * * ?")
    public void init() {
        logger.info("{}:elstic 增量同步index:{},type:{}", DateUtil.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"), ElasticConstant.INDEX_PERESON, ElasticConstant.TYPE_PERSON_PERSONINFO);
        try {
            String field = RedisConstant.getELasticCurid(ElasticConstant.TYPE_PERSON_PERSONINFO);
            String s = jedisClient.hget(ElasticConstant.INDEX_PERESON, field);
            Long startId = StringUtil.isEmpty(s) ? 1L : Long.parseLong(s);
            elasticPersonService.initData(startId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }
}
