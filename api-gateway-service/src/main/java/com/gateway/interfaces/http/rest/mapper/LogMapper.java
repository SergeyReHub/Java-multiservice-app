package com.gateway.interfaces.http.rest.mapper;

import com.gateway.application.dto.criteria.LogSearchCriteria;
import com.gateway.domain.model.Log;
import com.gateway.infrastructure.cache.redis.entity.LogRedisEntity;
import com.gateway.infrastructure.persistence.postgres.entity.LogJpaEntity;
import com.gateway.interfaces.http.rest.dto.GetLogsRequest;
import com.gateway.interfaces.http.rest.dto.GetLogsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface LogMapper {

    Log toDomain(LogJpaEntity entity);
    default Log toDomain(Object obj) {
        if (obj instanceof Log log) {
            return log;
        }
        throw new IllegalArgumentException("Expected Log, got: " + (obj == null ? "null" : obj.getClass()));
    }

    @Mapping(target = "date", source = "date", qualifiedByName = "stringToLocalDateTime")
    Log toDomain(GetLogsRequest entity);

    LogJpaEntity toJpaEntity(Log log);

    @Mapping(target = "date", source = "date", qualifiedByName = "localDateTimeToString")
    LogRedisEntity toRedisEntity(Log log);

    LogSearchCriteria toSearchCriteria(Log log);

    @Mapping(target="date", qualifiedByName = "localDateTimeToString")
    GetLogsResponse toGetLogsResponse(Log log);

    @Named("stringToLocalDateTime")
    default LocalDateTime stringToLocalDateTime(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            return LocalDateTime.parse(dateStr);
        } catch (Exception e) {
            // Fallback to date-only string
            return LocalDate.parse(dateStr).atStartOfDay();
        }
    }

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toString() : null;
    }

}