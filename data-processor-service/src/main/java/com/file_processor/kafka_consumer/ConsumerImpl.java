package com.file_processor.kafka_consumer;

import com.file_processor.Models.Log;
import com.file_processor.config.AppProperties;
import com.file_processor.file_processor.FileProcessor;
import com.file_processor.grpc_client.LogsSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Slf4j
@Component
public class ConsumerImpl implements Consumer {
    private final AppProperties appProperties;
    private final FileProcessor fileProcessor;
    private final LogsSender logsSender;

    public ConsumerImpl(AppProperties appProperties, FileProcessor fileProcessor, LogsSender logsSender) {
        this.appProperties = appProperties;
        this.fileProcessor = fileProcessor;
        this.logsSender = logsSender;
    }

    @Override
    @KafkaListener(topics = "logs", groupId = "group1", concurrency = "3")
    public void consume(String data) {
        log.info("Processing message in thread: {}", Thread.currentThread().getName());
        try {
            List<Log> logs = fileProcessor.processData(data);
            logsSender.sendLogs(logs);
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage(), e);
        }
    }
}
