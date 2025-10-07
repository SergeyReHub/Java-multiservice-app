package com.gateway.infrastructure.persistence.postgres.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class LogJpaEntity implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String message;
    private String error;
    private int statusCode;
    private LocalDateTime date;
    private String level;
}
