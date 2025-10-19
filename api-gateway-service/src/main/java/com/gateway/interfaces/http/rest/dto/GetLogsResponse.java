package com.gateway.interfaces.http.rest.dto;

import lombok.Data;

@Data
public class GetLogsResponse {
    private String id;
    private String date;
    private String message;
    private String level;
    private int statusCode;
    private String error;
}
