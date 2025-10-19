package com.gateway.application.exception_handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.gateway.application.exception_handler.exception_dto.ErrorResponseDto;
import org.springframework.web.context.request.WebRequest;

import java.net.ConnectException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.redis.RedisConnectionFailureException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound(EntityNotFoundException ex, WebRequest request, HttpServletRequest httpRequest) {
        String requestedId = extractIdFromPath(httpRequest.getRequestURI());
        Map<String, String> details = Map.of(
                "entity", "Log", // or extract from exception message
                "reason", "Entity not found in database",
                "requested_id", requestedId, // if you're looking up by ID
                "request_method", httpRequest.getMethod(),
                "request_uri", httpRequest.getRequestURI(),
                "detailed_problem", ex.getMessage()
        );
        ErrorResponseDto error = new ErrorResponseDto(
                "Entity not found",
                404,
                LocalDateTime.now(),
                request.getDescription(false),
                details
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({SQLFeatureNotSupportedException.class, DataAccessException.class, CannotGetJdbcConnectionException.class,
            RedisConnectionFailureException.class, CannotCreateTransactionException.class})
    public ResponseEntity<ErrorResponseDto> databaseError(Exception ex, HttpServletRequest httpRequest) {
        String errorMessage = "Database error";
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

        if (ex instanceof CannotGetJdbcConnectionException ||
                ex instanceof CannotCreateTransactionException ||
                ex.getCause() instanceof ConnectException) {
            errorMessage = "Unable to connect to the database service";
            statusCode = HttpStatus.SERVICE_UNAVAILABLE.value();
        }
        Map<String, String> details = Map.of(
                "entity", "Log",
                "detailed_problem", ex.getMessage(),
                "error_type", ex.getClass().getSimpleName()
        );
        ErrorResponseDto err = new ErrorResponseDto(
                errorMessage,
                statusCode,
                LocalDateTime.now(),
                httpRequest.getRequestURI(),
                details
        );
        // Nothing to do.  Returns the logical view name of an error page, passed
        // to the view-resolver(s) in usual way.
        // Note that the exception is NOT available to this view (it is not added
        // to the model) but see "Extending ExceptionHandlerExceptionResolver"
        // below.
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private String extractIdFromPath(String requestUri) {
        try {
            // Extract the last part of the path as the ID
            String[] pathSegments = requestUri.split("/");
            return pathSegments[pathSegments.length - 1];
        } catch (Exception e) {
            return "unknown";
        }
    }

}
