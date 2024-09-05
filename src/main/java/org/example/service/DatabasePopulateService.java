package org.example.service;

import org.example.db.ConnectionManager;
import org.example.db.SQLExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabasePopulateService {
    private static final Logger logger = LoggerFactory.getLogger(DatabasePopulateService.class);
    private final ConnectionManager connectionManager;

    public DatabasePopulateService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Inserts data into the database by executing SQL scripts.
     */
    public void insertData(String sqlFilePath) {
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