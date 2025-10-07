package com.gateway.interfaces.http.rest.dto;

import lombok.Data;

@Data
public class GetLogsRequest {
    private String date;
    private String message;
    private String level;
    private int statusCode;
    private String error;
}
