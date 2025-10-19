package com.gateway.infrastructure.cache.redis.repository;

import com.gateway.infrastructure.cache.redis.entity.LogRedisEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LogRedisRepositoryImpl implements LogRedisRepository {
    private static final String LOGS_KEY = "logs";
    private final RedisTemplate<String, LogRedisEntity> redisTemplate;

    @Override
    public void save(LogRedisEntity logRedisEntity) {
        redisTemplate.opsForZSet()
                .add(LOGS_KEY, logRedisEntity, logRedisEntity.getStatusCode());
    }

    @Override
    public List<LogRedisEntity> findByStatusCodeGreaterThanEqual(int statusCode) {
        Set<ZSetOperations.TypedTuple<LogRedisEntity>> logs = redisTemplate.opsForZSet()
                .rangeByScoreWithScores("log", statusCode, Double.POSITIVE_INFINITY);
        return logs.stream()
                .map(ZSetOperations.TypedTuple::getValue)
                .collect(Collectors.toList());
    }

    public void saveAll(List<LogRedisEntity> logs) {
        logs.forEach(this::save);
    }

    @Override
    public void deleteById(String id) {
        redisTemplate.opsForZSet().remove(LOGS_KEY, id);
    }
}

// refill the repository using cutom class that using
//public void save(List<LogRedisEntity> logRedisEntity) {
//    for (LogRedisEntity item : logRedisEntity) {
//        redisTemplate.opsForZSet()
//                .add(item.getId(), clonedItem, item.getVersion() (CHANGE IT TO YOUR GETTERS));
//    }
//}
// check https://stackoverflow.com/questions/65823143/spring-redis-range-query-greater-than-on-a-field

