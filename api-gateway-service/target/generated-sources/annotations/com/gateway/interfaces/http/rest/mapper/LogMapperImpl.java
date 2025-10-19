package com.gateway.interfaces.http.rest.mapper;

import com.gateway.application.dto.criteria.LogSearchCriteria;
import com.gateway.domain.model.Log;
import com.gateway.infrastructure.cache.redis.entity.LogRedisEntity;
import com.gateway.infrastructure.persistence.postgres.entity.LogJpaEntity;
import com.gateway.interfaces.http.rest.dto.GetLogsRequest;
import com.gateway.interfaces.http.rest.dto.GetLogsResponse;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-19T13:33:41+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Ubuntu)"
)
@Component
public class LogMapperImpl implements LogMapper {

    @Override
    public Log toDomain(LogJpaEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Log.LogBuilder log = Log.builder();

        log.id( entity.getId() );
        log.date( entity.getDate() );
        log.message( entity.getMessage() );
        log.level( entity.getLevel() );
        log.statusCode( entity.getStatusCode() );
        log.error( entity.getError() );

        return log.build();
    }

    @Override
    public Log toDomain(GetLogsRequest entity) {
        if ( entity == null ) {
            return null;
        }

        Log.LogBuilder log = Log.builder();

        log.date( stringToLocalDateTime( entity.getDate() ) );
        log.id( entity.getId() );
        log.message( entity.getMessage() );
        log.level( entity.getLevel() );
        log.statusCode( entity.getStatusCode() );
        log.error( entity.getError() );

        return log.build();
    }

    @Override
    public LogJpaEntity toJpaEntity(Log log) {
        if ( log == null ) {
            return null;
        }

        LogJpaEntity logJpaEntity = new LogJpaEntity();

        logJpaEntity.setId( log.getId() );
        logJpaEntity.setMessage( log.getMessage() );
        logJpaEntity.setError( log.getError() );
        logJpaEntity.setStatusCode( log.getStatusCode() );
        logJpaEntity.setDate( log.getDate() );
        logJpaEntity.setLevel( log.getLevel() );

        return logJpaEntity;
    }

    @Override
    public LogRedisEntity toRedisEntity(Log log) {
        if ( log == null ) {
            return null;
        }

        LogRedisEntity logRedisEntity = new LogRedisEntity();

        logRedisEntity.setDate( localDateTimeToString( log.getDate() ) );
        logRedisEntity.setId( log.getId() );
        logRedisEntity.setMessage( log.getMessage() );
        logRedisEntity.setLevel( log.getLevel() );
        logRedisEntity.setStatusCode( log.getStatusCode() );
        logRedisEntity.setError( log.getError() );

        return logRedisEntity;
    }

    @Override
    public LogSearchCriteria toSearchCriteria(Log log) {
        if ( log == null ) {
            return null;
        }

        LogSearchCriteria logSearchCriteria = new LogSearchCriteria();

        logSearchCriteria.setId( log.getId() );
        if ( log.getDate() != null ) {
            logSearchCriteria.setDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( log.getDate() ) );
        }
        logSearchCriteria.setMessage( log.getMessage() );
        logSearchCriteria.setLevel( log.getLevel() );
        logSearchCriteria.setStatusCode( log.getStatusCode() );
        logSearchCriteria.setError( log.getError() );

        return logSearchCriteria;
    }

    @Override
    public GetLogsResponse toGetLogsResponse(Log log) {
        if ( log == null ) {
            return null;
        }

        GetLogsResponse getLogsResponse = new GetLogsResponse();

        getLogsResponse.setDate( localDateTimeToString( log.getDate() ) );
        getLogsResponse.setId( log.getId() );
        getLogsResponse.setMessage( log.getMessage() );
        getLogsResponse.setLevel( log.getLevel() );
        getLogsResponse.setStatusCode( log.getStatusCode() );
        getLogsResponse.setError( log.getError() );

        return getLogsResponse;
    }
}
