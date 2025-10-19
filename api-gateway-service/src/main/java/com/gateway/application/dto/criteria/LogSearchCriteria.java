package com.gateway.application.dto.criteria;

import lombok.Data;

@Data
public class LogSearchCriteria {
    private String id;
    private String date;
    private String message;
    private String level;
    private Integer statusCode;
    private String error;

}