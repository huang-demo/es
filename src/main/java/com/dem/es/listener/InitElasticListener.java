package com.dem.es.listener;

import com.dem.es.service.ElasticBaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class InitElasticListener implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    private final Logger log = Logger.getLogger(InitElasticListener.class);

    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.ctx = context;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, ElasticBaseService> beans = ctx.getBeansOfType(ElasticBaseService.class);
        for (ElasticBaseService baseService : beans.values()) {
            baseService.init();
        }
    }
}
