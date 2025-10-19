package com.gateway.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Log {
    private String id;
    private LocalDateTime date;
    private String message;
    private String level;
    private int statusCode;
    private String error;
}
