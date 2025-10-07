package com.gateway.interfaces.http.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLogsByDateResponse {
    private String date;
    private String message;
    private String level;
    private int statusCode;
    private String error;
}
