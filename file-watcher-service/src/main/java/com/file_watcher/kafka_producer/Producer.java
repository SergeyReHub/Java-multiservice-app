package com.file_watcher.kafka_producer;

import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Interface for sending file content to Kafka topics.
 */
@Component
public interface Producer {
    /**
     * Sends file content to a Kafka topic.
     *
     * @param topic    The Kafka topic to send the message to
     * @param key      The key for the Kafka message
     * @param filePath The path to the file whose content should be sent
     */
    void send(String topic, String key, Path filePath);
    
    /**
     * Sends file content to the default Kafka topic.
     *
     * @param key      The key for the Kafka message
     * @param filePath The path to the file whose content should be sent
     */
    default void send(String key, Path filePath) {
        send(null, key, filePath);
    }
}
