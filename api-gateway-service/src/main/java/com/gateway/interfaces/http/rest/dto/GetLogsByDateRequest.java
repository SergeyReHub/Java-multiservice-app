package com.gateway.interfaces.http.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetLogsByDateRequest {
    LocalDateTime dateTime1;
    LocalDateTime dateTime2;
}
