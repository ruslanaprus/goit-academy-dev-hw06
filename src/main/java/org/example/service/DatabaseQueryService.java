package org.example.service;

import org.example.db.ConnectionManager;
import org.example.db.SQLExecutor;
import org.example.viewmodel.MaxSalaryWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseQueryService {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseQueryService.class);
    private final ConnectionManager connectionManager;

    public DatabaseQueryService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public List<MaxSalaryWorker> findMaxSalaryWorker(String sqlFilePath) {
        Path path = Paths.get(sqlFilePath);
        List<MaxSalaryWorker> workers = new ArrayList<>();
        try {
            String sqlContent = new String(Files.readAllBytes(path));
            SQLExecutor executor = new SQLExecutor(connectionManager.getConnection());
            Optional<ResultSet> resultSetOpt = executor.executeQuery(sqlContent);

            if (resultSetOpt.isPresent()) {
                try (ResultSet resultSet = resultSetOpt.get()) {
                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        int salary = resultSet.getInt("highest_salary");
                        workers.add(new MaxSalaryWorker(name, salary));
                    }
                }
            } else {
                logger.warn("No data found for max salary worker");
            }
        } catch (IOException | SQLException e) {
            logger.error("Error occurred while querying max salary worker", e);
        } finally {
            connectionManager.close();
        }
        return workers;
    }
}