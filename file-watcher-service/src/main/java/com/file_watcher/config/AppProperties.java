package com.file_watcher.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private Watch watch;
    private Kafka kafka;

    @Getter
    @Setter
    public static class Watch {
        private String directory;
        private List<String> patterns;
    }

    @Getter
    @Setter
    public static class Kafka {
        private String topic;
        private String bootstrapServers;
    }
}
