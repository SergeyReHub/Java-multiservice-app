package com.file_processor.file_processor;

import com.file_processor.Models.Log;

import java.util.List;

public interface FileProcessor {
    public List<Log> processData(String data);
}
