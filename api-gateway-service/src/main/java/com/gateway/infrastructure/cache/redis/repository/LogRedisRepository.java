package com.gateway.infrastructure.cache.redis.repository;

import com.gateway.infrastructure.cache.redis.entity.LogRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface LogRedisRepository  {
    void save(LogRedisEntity redisEntity);
    List<LogRedisEntity> findByStatusCodeGreaterThanEqual(int statusCode);
}

