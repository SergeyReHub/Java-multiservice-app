package com.gateway.interfaces.http.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetLogsByDateRequest {
    @NotNull
    LocalDateTime dateTime1;
    @NotNull
    LocalDateTime dateTime2;
}
