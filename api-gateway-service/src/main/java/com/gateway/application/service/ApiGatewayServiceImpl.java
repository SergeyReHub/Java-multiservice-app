package com.gateway.application.service;

import com.gateway.application.dto.criteria.LogSearchCriteria;
import com.gateway.domain.model.Log;
import com.gateway.infrastructure.cache.redis.repository.LogRedisRepository;
import com.gateway.infrastructure.persistence.postgres.entity.LogJpaEntity;
import com.gateway.infrastructure.persistence.postgres.repository.LogRepository;
import com.gateway.infrastructure.persistence.postgres.specification.LogSpecification;
import com.gateway.interfaces.http.rest.mapper.LogMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApiGatewayServiceImpl implements ApiGatewayService {
    private final LogRepository logRepository;
    private final LogRedisRepository logRedisRepository;
    private final LogMapper logMapper;


    public ApiGatewayServiceImpl(LogRepository logRepository, LogRedisRepository logRedisRepository, LogMapper logMapper) {
        this.logRepository = logRepository;
        this.logRedisRepository = logRedisRepository;
        this.logMapper = logMapper;
    }

    @Override
    public List<Log> getLogs(Log log) {
        LogSearchCriteria criteria = logMapper.toSearchCriteria(log);
        Specification<LogJpaEntity> spec = LogSpecification.withCriteria(criteria);
        List<LogJpaEntity> logJpaEntities = logRepository.findAll(spec);
        return logJpaEntities.stream().map(logMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Log getLogByID(String id) {
        Optional<LogJpaEntity> log = logRepository.findById(id);
        if (log.isEmpty()) {
            throw new EntityNotFoundException("Log not found");
        }
        return logMapper.toDomain(log.get());
    }

    @Override
    public List<Log> getLogsByDates(LocalDateTime date1, LocalDateTime date2) {
        List<LogJpaEntity> logJpaEntities = logRepository.getLogsByDateBetween(date1, date2);
        return logJpaEntities.stream().map(logMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Log> findAll5xx() {
        String key = "logs_5xx";

        List<Log> logs5xx = Objects.requireNonNull(logRedisRepository.findByStatusCodeGreaterThanEqual(500))
                .stream()
                .map(logMapper::toDomain)
                .toList();

        if (logs5xx.isEmpty()) {
            logs5xx = logRepository.findAll5xx().stream()
                    .map(logMapper::toDomain)
                    .toList();
        }
        if (logs5xx.isEmpty()) {
            throw new EntityNotFoundException("Logs 5xx not found");
        }
        return logs5xx;
    }


    @Override
    public boolean deleteLogById(String id) {
        return false;
    }

}
