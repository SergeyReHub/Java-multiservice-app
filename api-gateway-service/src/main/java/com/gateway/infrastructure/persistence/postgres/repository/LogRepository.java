package com.gateway.infrastructure.persistence.postgres.repository;

import com.gateway.infrastructure.persistence.postgres.entity.LogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LogRepository extends JpaRepository<LogJpaEntity, String>, JpaSpecificationExecutor<LogJpaEntity> {
    List<LogJpaEntity> getLogsByDateBetween(LocalDateTime date1, LocalDateTime date2);

    @Query("SELECT l FROM LogJpaEntity l WHERE l.statusCode >= 500")
    List<LogJpaEntity> findAll5xx();

    @Modifying
    @Query("DELETE FROM LogJpaEntity l WHERE l.id = :id")
    int deleteLogById(@Param("id") String id);
}
