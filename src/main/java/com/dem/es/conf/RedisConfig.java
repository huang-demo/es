package com.dem.es.conf;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * 单机版配置
 */
@Configuration
public class RedisConfig {
    private static Logger logger = Logger.getLogger(RedisConfig.class);
    @Getter
    @Setter
    @Value("${spring.redis.host}")
    private String host;
    @Getter
    @Setter
    @Value("${spring.redis.port}")
    private int port;


    @Bean
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }


    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig config = getRedisConfig();
        JedisPool pool = new JedisPool(config, host, port);
        logger.info("init JredisPool ...");
        return pool;
    }

}
