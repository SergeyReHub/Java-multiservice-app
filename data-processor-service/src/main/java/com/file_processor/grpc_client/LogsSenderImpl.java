package com.file_processor.grpc_client;

import com.file_processor.Models.Log;
import com.file_processor.mapper_grpc.LogMapper;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class LogsSenderImpl implements LogsSender {

    @GrpcClient("logs-sender") // matches grpc.client.logs-sender.address in config
    private LogsSenderGrpc.LogsSenderStub asyncStub;

    private final LogMapper logMapper;

    public LogsSenderImpl(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    public void sendLogs(List<Log> logs) throws InterruptedException{
        List<SenderService.Log> grpcLogs = logMapper.toGrpcLog(logs);

        CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<Empty> responseObserver = new StreamObserver<Empty>() {
            @Override
            public void onNext(Empty empty) {
                log.info("Server acknowledged logs (Empty).");
            }

            @Override
            public void onError(Throwable t) {
                log.error("Error from server: {}", t.getMessage(), t);
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                log.info("Server completed.");
                finishLatch.countDown();
            }
        };

        // Start the bidirectional stream
        StreamObserver<SenderService.Log> requestObserver = asyncStub.sendLogs(responseObserver);


        try {
            // Send each log in the list
            for (SenderService.Log log : grpcLogs) {
                requestObserver.onNext(log);
            }

            // Signal that we're done sending
            requestObserver.onCompleted();

        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        }
    }

}
