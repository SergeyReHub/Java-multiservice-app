package com.file_processor.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {
    private Kafka kafka;

    @Setter
    @Getter
    public static class Kafka {
        private String topic;
        private String bootstrapServers;
    }

}
