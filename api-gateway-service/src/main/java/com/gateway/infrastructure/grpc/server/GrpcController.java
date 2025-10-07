package com.gateway.infrastructure.grpc.server;

import com.file_processor.grpc_client.LogsSenderGrpc;
import com.file_processor.grpc_client.SenderService;
import com.gateway.infrastructure.cache.redis.entity.LogRedisEntity;
import com.gateway.infrastructure.cache.redis.repository.LogRedisRepository;
import com.gateway.infrastructure.persistence.postgres.entity.LogJpaEntity;
import com.gateway.infrastructure.persistence.postgres.repository.LogRepository;
import com.gateway.interfaces.http.rest.mapper.LogMapper;
import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@Slf4j  // Add this annotation if you're using Lombok, or create a Logger manually
public class GrpcController extends LogsSenderGrpc.LogsSenderImplBase {
    private final LogMapper logMapper;
    private final LogRepository logRepository;
    private final LogRedisRepository logRedisRepository;

    public GrpcController(LogMapper logMapper, LogRepository logRepository, LogRedisRepository logRedisRepository) {
        this.logMapper = logMapper;
        this.logRepository = logRepository;
        this.logRedisRepository = logRedisRepository;
    }

    @Override
    public StreamObserver<SenderService.Log> sendLogs(StreamObserver<Empty> responseObserver) {
        return new StreamObserver<SenderService.Log>() {
            @Override
            public void onNext(SenderService.Log grpcLog) {
                try {
                    log.info("Received log: {}", grpcLog);

                    // Map gRPC message -> domain -> entity, then persist
                    com.gateway.domain.model.Log domain = com.gateway.domain.model.Log.builder()
                            .date(parseDate(grpcLog.getDate()))
                            .message(grpcLog.getMessage())
                            .level(grpcLog.getLevel())
                            .statusCode(grpcLog.getStatusCode())
                            .error(grpcLog.getError())
                            .build();

                    log.info("Mapped to domain: {}", domain);

                    LogRedisEntity logRedisEntity = logMapper.toRedisEntity(domain);
                    log.info("Mapped to Redis entity: {}", logRedisEntity);

                    if (saveIf5xx(logRedisEntity.getStatusCode())) {
                        log.info("Saving to Redis...");
                        logRedisRepository.save(logRedisEntity);
                    }

                    log.info("Saving to PostgreSQL...");
                    LogJpaEntity jpaEntity = logMapper.toJpaEntity(domain);
                    logRepository.save(jpaEntity);
                    log.info("Successfully saved log with ID: {}", jpaEntity.getId());

                } catch (Exception e) {
                    log.error("Error processing log: " + grpcLog, e);
                    responseObserver.onError(Status.INTERNAL
                            .withDescription("Error processing log: " + e.getMessage())
                            .asRuntimeException());
                }
            }

            @Override
            public void onError(Throwable t) {
                log.error("Error in gRPC stream", t);
            }

            @Override
            public void onCompleted() {
                log.info("gRPC stream completed");
                responseObserver.onNext(Empty.getDefaultInstance());
                responseObserver.onCompleted();
            }
        };
    }

    private java.time.LocalDateTime parseDate(String s) {
        if (s == null || s.isEmpty()) {
            log.warn("Received null or empty date, using current time");
            return java.time.LocalDateTime.now();
        }
        try {
            return java.time.LocalDateTime.parse(s);
        } catch (Exception e) {
            log.warn("Failed to parse date: {}, using current time", s, e);
            return java.time.LocalDateTime.now();
        }
    }

    private boolean saveIf5xx(int statusCode) {
        return statusCode >= 500 && statusCode < 600;
    }
}