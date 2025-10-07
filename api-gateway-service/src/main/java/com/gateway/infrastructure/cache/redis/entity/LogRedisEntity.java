package com.gateway.infrastructure.cache.redis.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@RedisHash("log") // key prefix, e.g. log:{id}
public class LogRedisEntity {
    @Id
    private String id;
    private String date;
    private String message;
    private String level;
    @Indexed
    private int statusCode;
    private String error;
}