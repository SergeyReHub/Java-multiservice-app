package com.file_processor.mapper_grpc;

import com.file_processor.Models.Log;
import com.file_processor.grpc_client.SenderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class LogMapper {
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public List<SenderService.Log> toGrpcLog(List<Log> domainLogs) {
        List<SenderService.Log> grpcLogs = new ArrayList<>();
        for (Log log : domainLogs){
            grpcLogs.add(SenderService.Log.newBuilder()
                    .setDate(log.getDate() != null ? log.getDate().format(DATE_FORMATTER) : "")
                    .setMessage(log.getMessage() != null ? log.getMessage() : "")
                    .setLevel(log.getLevel() != null ? log.getLevel() : "")
                    .setStatusCode(log.getStatusCode())
                    .setError(log.getError() != null ? log.getError() : "")
                    .build());
        }
        return grpcLogs;
    }
}
