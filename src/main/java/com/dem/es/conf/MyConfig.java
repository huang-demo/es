package com.dem.es.conf;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * elatisearch 配置
 */
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class MyConfig {

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String customName;
    @Value("${spring.elasticsearch.jest.uris}")
    private String uris;
    @Value("${isDev}")
    private boolean isDev;
    @Value("${spring.elasticsearch.jest.username}")
    private String xpackUser;
    @Value("${spring.elasticsearch.jest.password}")
    private String xpackPass;

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
