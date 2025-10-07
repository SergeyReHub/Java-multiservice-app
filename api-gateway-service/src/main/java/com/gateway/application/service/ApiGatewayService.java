package com.gateway.application.service;

import com.gateway.application.dto.criteria.LogSearchCriteria;
import com.gateway.domain.model.Log;

import java.time.LocalDateTime;
import java.util.List;

public interface ApiGatewayService {
    public List<Log> getLogs(Log log);
    public Log getLogByID (String id);
    public List<Log> getLogsByDates(LocalDateTime date1, LocalDateTime date2);
    public List<Log> findAll5xx();
    public boolean deleteLogById (String id);

}
