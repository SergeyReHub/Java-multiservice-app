package com.gateway.application.exception_handler.exception_dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    private String error;
    private int status;
    private LocalDateTime timestamp;
    private String path;
    private Map<String, String> details;
}
