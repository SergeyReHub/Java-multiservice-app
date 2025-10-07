package com.gateway.infrastructure.persistence.postgres.repository;

import com.gateway.infrastructure.persistence.postgres.entity.LogJpaEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LogRepository extends CrudRepository<LogJpaEntity, String>, JpaSpecificationExecutor<LogJpaEntity> {
        List<LogJpaEntity> getLogsByDateBetween(LocalDateTime date1, LocalDateTime date2);

        @Query("SELECT l FROM LogJpaEntity l WHERE l.statusCode >= 500")
        List<LogJpaEntity> findAll5xx();
}
