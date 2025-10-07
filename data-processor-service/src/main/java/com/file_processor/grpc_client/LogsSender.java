package com.file_processor.grpc_client;

import com.file_processor.Models.Log;

import java.util.List;

public interface LogsSender {
    void sendLogs(List<Log> logs) throws InterruptedException;
}
