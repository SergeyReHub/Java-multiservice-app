package com.gateway.interfaces.http.rest.controller;

import com.gateway.application.service.ApiGatewayService;
import com.gateway.domain.model.Log;
import com.gateway.interfaces.http.rest.dto.GetLogsByDateRequest;
import com.gateway.interfaces.http.rest.dto.GetLogsByDateResponse;
import com.gateway.interfaces.http.rest.dto.GetLogsRequest;
import com.gateway.interfaces.http.rest.dto.GetLogsResponse;
import com.gateway.interfaces.http.rest.mapper.LogMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/api")
@RestController
public class ApiGatewayController {
    private final ApiGatewayService apiGatewayService;
    private final LogMapper logMapper;

    public ApiGatewayController(ApiGatewayService apiGatewayService, LogMapper logMapper) {
        this.apiGatewayService = apiGatewayService;
        this.logMapper = logMapper;
    }

    @PostMapping("/logs/get_logs")
    public ResponseEntity<List<GetLogsResponse>> getLogs(@RequestBody @Valid GetLogsRequest payload) {
        Log logPayload = logMapper.toDomain(payload);
        List<Log> logs = apiGatewayService.getLogs(logPayload);
        return ResponseEntity.ok(logs.stream().map(logMapper::toGetLogsResponse).collect(Collectors.toList()));
    }

    @PostMapping("/logs/by_dates")
    public ResponseEntity<List<GetLogsResponse>> getLogsByDates(@RequestBody @Valid GetLogsByDateRequest payload) {
        List<Log> logs = apiGatewayService.getLogsByDates(payload.getDateTime1(), payload.getDateTime2());
        return ResponseEntity.ok(logs.stream().map(logMapper::toGetLogsResponse).collect(Collectors.toList()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ID успешно найден"),
            @ApiResponse(responseCode = "404", description = "ID не найден")
    })
    @GetMapping("/logs/{id}")
    public ResponseEntity<GetLogsResponse> getLogByID(@PathVariable String id) {
        Log log = apiGatewayService.getLogByID(id);
        return ResponseEntity.ok(logMapper.toGetLogsResponse(log));
    }

    @GetMapping("logs/get_5xx")
    public ResponseEntity<List<GetLogsResponse>> getLogsByStatus5xx() {
        List<Log> logs = apiGatewayService.findAll5xx();
        return ResponseEntity.ok(logs.stream().map(logMapper::toGetLogsResponse).collect(Collectors.toList()));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Log deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Log not found")
    })
    @DeleteMapping("logs/delete_by_id/{id}")
    public ResponseEntity<String> deleteLogById(@PathVariable String id) {
        apiGatewayService.deleteLogById(id);
        return ResponseEntity.ok("Log deleted successfully");
    }

//    @PostMapping("/logs")
//    public void sendLogs(@RequestBody List<Log> logs) {
//        apiGatewayService.sendLogs(logs);
//    }
}
