package com.dem.es.conf;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * elatisearch 配置
 */

@Configuration
public class ElasticClientConfig {

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String customName;
    @Value("${spring.elasticsearch.jest.uris}")
    private String uris;
    @Value("${elastic.isdev}")
    private boolean isDev;
    @Value("${spring.elasticsearch.jest.username}")
    private String xpackUser;
    @Value("${spring.elasticsearch.jest.password}")
    private String xpackPass;

    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
    @Bean
    public TransportClient client() throws UnknownHostException {
        TransportClient client = null;
        if (isDev) {
            Settings settings = Settings.builder()
                    .put("cluster.name", customName)
                    .put("client.transport.sniff", true).build();
            client = new PreBuiltTransportClient(settings);
            for (String uri : uris.split(",")) {
                String[] split = uri.split(":");
                client.addTransportAddress(new TransportAddress(InetAddress.getByName(split[0]), Integer.valueOf(split[1])));
            }
        } else {
            Settings settings = Settings.builder().put("cluster.name", customName)
                    .put("xpack.security.transport.ssl.enabled", false)
                    .put("xpack.security.user", xpackUser + ":" + xpackPass)
                    .put("client.transport.sniff", true).build();

            client = new PreBuiltXPackTransportClient(settings);
            for (String uri : uris.split(",")) {
                String[] split = uri.split(":");
                client.addTransportAddress(new TransportAddress(InetAddress.getByName(split[0]), Integer.valueOf(split[1])));
            }
        }
        return client;
    }
}
