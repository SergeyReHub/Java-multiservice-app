package com.gateway.infrastructure.persistence.postgres.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "log_jpa_entity")
public class LogJpaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String message;
    private String error;
    private int statusCode;
    private LocalDateTime date;
    private String level;
}
