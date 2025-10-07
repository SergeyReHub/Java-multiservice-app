package com.file_processor.file_processor;

import com.file_processor.Models.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class FileProcesorImpl implements FileProcessor {
    
    // Regex pattern to match quoted strings or unquoted words
    private static final String QUOTED_STRING = "\"([^\"]*)\"|(\\S+)";
    private static final Pattern LOG_PATTERN = Pattern.compile(
            "(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})\\s+" +  // date (group 1)
                    "(\\w+)\\s+" +                                       // level (group 2)
                    "(\\d{3})\\s+" +                                    // status code (group 3)
                    "(\"[^\"]*\"|\\S+)\\s+" +                           // error (group 4)
                    "(\".*\"|\\S*)"                                     // message (group 5)
    );

    @Override
    public List<Log> processData(String data) {
        log.info("Processing data: {}", data);
        List<Log> logs = new ArrayList<>();
        if (data == null || data.trim().isEmpty()) {
            return logs;
        }

        String[] lines = data.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            try {
                Matcher matcher = LOG_PATTERN.matcher(line);
                if (matcher.find()) {
                    String dateStr = matcher.group(1);
                    String level = matcher.group(2);
                    int statusCode = Integer.parseInt(matcher.group(3));
                    // Remove surrounding quotes if present
                    String error = matcher.group(4).replaceAll("^\"|\"$", "");
                    String message = matcher.group(5).replaceAll("^\"|\"$", "");

                    Log log = Log.builder()
                            .date(parseDateTime(dateStr))
                            .level(level)
                            .statusCode(statusCode)
                            .error(error)
                            .message(message)
                            .build();
                    logs.add(log);
                } else {
                    log.warn("Log line didn't match pattern: {}", line);
                }
            } catch (Exception e) {
                log.error("Error parsing log line '{}': {}", line, e.getMessage(), e);
            }
        }
        
        return logs;
    }
    
    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr);
        } catch (DateTimeParseException e) {
            log.warn("Invalid date format: {}", dateTimeStr);
            return LocalDateTime.now(); // Return current time as fallback
        }
    }
}
