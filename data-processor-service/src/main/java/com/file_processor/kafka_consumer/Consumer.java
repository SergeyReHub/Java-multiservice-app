package com.file_processor.kafka_consumer;

import java.nio.file.Path;

public interface Consumer {
    public void consume(String data);
}
