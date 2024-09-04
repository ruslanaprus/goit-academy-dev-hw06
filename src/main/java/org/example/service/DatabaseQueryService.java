package org.example.service;

import org.example.db.ConnectionManager;
import org.example.db.SQLExecutor;
import org.example.viewmodel.MaxProjectCountClient;
import org.example.viewmodel.MaxSalaryWorker;
import org.example.viewmodel.ProjectPriceInfo;

import java.util.List;

public class DatabaseQueryService {
    private final ConnectionManager connectionManager;

    public DatabaseQueryService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public List<MaxSalaryWorker> findMaxSalaryWorker(String sqlFilePath) {
        String errorMessage = "Failed to execute findMaxSalaryWorker query";

        try (SQLExecutor executor = new SQLExecutor(connectionManager.getConnection())) {
            return executor.executeQuery(
                    sqlFilePath,
                    errorMessage,
                    rs -> {
                        String name = rs.getString("name");
                        int highestSalary = rs.getInt("highest_salary");
                        return new MaxSalaryWorker(name, highestSalary);
                    }
            );
        }
    }

    public List<MaxProjectCountClient> findMaxProjectsClient(String sqlFilePath) {
        String errorMessage = "Failed to execute findMaxProjectsClient query";

        try (SQLExecutor executor = new SQLExecutor(connectionManager.getConnection())) {
            return executor.executeQuery(
                    sqlFilePath,
                    errorMessage,
                    rs -> {
                        String client_name = rs.getString("client_name");
                        int project_count = rs.getInt("project_count");
                        return new MaxProjectCountClient(client_name, project_count);
                    }
            );
        }
    }

    public List<ProjectPriceInfo> printProjectPrices(String sqlFilePath) {
        String errorMessage = "Failed to execute printProjectPrices query";

        try (SQLExecutor executor = new SQLExecutor(connectionManager.getConnection())) {
            return executor.executeQuery(
                    sqlFilePath,
                    errorMessage,
                    rs -> {
                        String project_name = rs.getString("project_name");
                        int project_price = rs.getInt("project_price");
                        return new ProjectPriceInfo(project_name, project_price);
                    }
            );
        }
    }
}