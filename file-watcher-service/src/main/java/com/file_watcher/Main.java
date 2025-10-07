package com.file_watcher;

import com.file_watcher.config.AppProperties;
import com.file_watcher.kafka_producer.Producer;
import com.file_watcher.service.FileWatcherService;
import com.file_watcher.service.FileWatcherServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Main implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(Main.class);

    private final FileWatcherService fileWatcherService;

    public Main(FileWatcherService fileWatcherServiceImpl) {
        this.fileWatcherService = fileWatcherServiceImpl;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            fileWatcherService.startFileWatcher();
        } catch (Exception e) {
            logger.error("Error in file watcher", e);
            System.exit(1);
        }
    }


}
