package org.example.service;

import org.example.db.ConnectionManager;
import org.example.db.SQLExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabaseInitService {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitService.class);
    private final ConnectionManager connectionManager;

    public DatabaseInitService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Initializes the database by executing SQL scripts to create tables.
     */
    public void initializeDatabase(String sqlFilePath) {
        Path path = Paths.get(sqlFilePath);
        try {
            String sqlContent = new String(Files.readAllBytes(path));
            SQLExecutor executor = new SQLExecutor(connectionManager.getConnection());
            executor.executeBatch(sqlContent);
        } catch (IOException e) {
            logger.error("Failed to read SQL file: {}", e.getMessage());
        }
    }
}