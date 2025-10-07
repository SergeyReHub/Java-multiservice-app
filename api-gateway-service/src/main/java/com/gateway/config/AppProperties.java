package com.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {
    private RdConf rdConf;

    @Getter
    @Setter
    public static class RdConf {
        private String hostname;
        private int port;
    }
}
