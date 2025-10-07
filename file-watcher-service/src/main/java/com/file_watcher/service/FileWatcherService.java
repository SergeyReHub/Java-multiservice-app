package com.file_watcher.service;

import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

@Component
public interface FileWatcherService {
    public void startFileWatcher() throws Exception;
}
