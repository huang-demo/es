package com.dem.es.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * 未测试
 */
//@Configuration
public class RedisClusterConfig {

    @Getter
    @Setter
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;

//    @Bean
    public JedisCluster getJedisCluster() {
        Set<HostAndPort> hostAndPorts = new HashSet<>();
        String[] split = this.clusterNodes.split(",");
        for (String s : split) {
            String[] node = s.split(":");
            hostAndPorts.add(new HostAndPort(node[0], Integer.valueOf(node[1])));
        }
        JedisCluster jedisCluster = new JedisCluster(hostAndPorts);
        return jedisCluster;
    }
}
