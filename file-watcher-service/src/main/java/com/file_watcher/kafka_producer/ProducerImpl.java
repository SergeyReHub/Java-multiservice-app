package com.file_watcher.kafka_producer;

import com.file_watcher.config.AppProperties;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Implementation of the Producer interface that sends file contents to Kafka.
 */
@Service
public class ProducerImpl implements Producer {
    private static final Logger logger = LogManager.getLogger(ProducerImpl.class);
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String defaultTopic;

    /**
     * Constructs a new ProducerImpl with the specified Kafka template and application properties.
     *
     * @param kafkaTemplate The Kafka template to use for sending messages
     * @param appProperties The application properties containing Kafka configuration
     */
    public ProducerImpl(KafkaTemplate<String, String> kafkaTemplate, AppProperties appProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.defaultTopic = appProperties.getKafka().getTopic();
    }

    @Override
    public void send(String topic, String key, Path filePath) {
        String targetTopic = topic != null ? topic : defaultTopic;
        
        try {
            String content = Files.readString(filePath);
            ProducerRecord<String, String> record = new ProducerRecord<>(
                targetTopic, 
                key, 
                content
            );
            
            kafkaTemplate.send(record).whenComplete((result, ex) -> {
                if (ex == null) {
                    logger.debug("Successfully sent message to topic: {}", targetTopic);
                } else {
                    logger.error("Failed to send message to topic: {}", targetTopic, ex);
                }
            });
            
            logger.info("Sent file content to topic '{}' with key '{}'", targetTopic, key);
        } catch (IOException e) {
            String errorMsg = String.format("Error reading file: %s", filePath);
            logger.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }
}
