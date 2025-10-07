package com.file_watcher.service;

import com.file_watcher.config.AppProperties;
import com.file_watcher.kafka_producer.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.nio.file.*;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileWatcherServiceImpl implements  FileWatcherService {
    private final AppProperties appProperties;
    private final Producer producer;

    FileWatcherServiceImpl(AppProperties appProperties, Producer producer) {
        this.appProperties = appProperties;
        this.producer = producer;
    }

    @Override
    public void startFileWatcher() throws Exception {
        Path watchDir = Paths.get(appProperties.getWatch().getDirectory()).toAbsolutePath().normalize();

        log.info("Watching directory (resolved path): {}", watchDir.toAbsolutePath());

        // Create the directory if it doesn't exist
        if (Files.notExists(watchDir)) {
            Files.createDirectories(watchDir);
            log.info("Created watch directory: {}", watchDir);
        }

        WatchService watcher = FileSystems.getDefault().newWatchService();

        // Get the list of event kinds to watch from configuration
        Set<WatchEvent.Kind<Path>> eventKinds = appProperties.getWatch().getPatterns().stream()
                .map(String::toUpperCase)
                .map(this::mapToWatchEventKind)
                .collect(Collectors.toSet());

        // Register the directory with the watch service for the specified events
        watchDir.register(watcher, eventKinds.toArray(new WatchEvent.Kind[0]));

        log.info("Watching directory: {} for events: {}",
                watchDir,
                String.join(", ", appProperties.getWatch().getPatterns()));

        // Process events
        while (true) {
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("File watcher was interrupted");
                break;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                processWatchEvent(event, watchDir);
            }

            if (!key.reset()) {
                log.warn("Watch key is no longer valid");
                break;
            }
        }
    }


    private void processWatchEvent(WatchEvent<?> event, Path watchDir) {
        WatchEvent.Kind<?> kind = event.kind();

        if (kind == StandardWatchEventKinds.OVERFLOW) {
            log.warn("File system overflow occurred");
            return;
        }

        @SuppressWarnings("unchecked")
        WatchEvent<Path> ev = (WatchEvent<Path>) event;
        Path filename = watchDir.resolve(ev.context());
        String eventType = kind.name();

        log.debug("File event: {} - {}", eventType, filename);

        try {
            if (kind == StandardWatchEventKinds.ENTRY_CREATE || kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                if (Files.isRegularFile(filename)) {
                    log.info("Processing {} event for file: {}", eventType, filename);
                    producer.send(filename.getFileName().toString(), filename);
                }
            } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                log.info("File deleted: {}", filename);
                // Handle file deletion if needed
            }
        } catch (Exception e) {
            log.error("Error processing file event for {}: {}", filename, e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private WatchEvent.Kind<Path> mapToWatchEventKind(String kindName) {
        return switch (kindName) {
            case "ENTRY_CREATE" -> StandardWatchEventKinds.ENTRY_CREATE;
            case "ENTRY_MODIFY" -> StandardWatchEventKinds.ENTRY_MODIFY;
            case "ENTRY_DELETE" -> StandardWatchEventKinds.ENTRY_DELETE;
            default -> throw new IllegalArgumentException("Unsupported watch event kind: " + kindName);
        };
    }
}
