package com.dem.es.service.impl;

import com.dem.es.service.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

//@Component
public class JedisClientCluster implements JedisClient {
    //    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public String set(String key, String value) {
        return jedisCluster.set(key, value);
    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public Long hset(String key, String item, String value) {
        return null;
    }

    @Override
    public String hget(String key, String item) {
        return jedisCluster.hget(key, item);
    }

    @Override
    public Long incr(String key) {
        return null;
    }

    @Override
    public Long decr(String key) {
        return null;
    }

    @Override
    public Long expire(String key, int second) {
        return null;
    }

    @Override
    public Long ttl(String key) {
        return null;
    }

    @Override
    public Long del(String key) {
        return null;
    }

    @Override
    public Long hdel(String key, String item) {
        return null;
    }
}
