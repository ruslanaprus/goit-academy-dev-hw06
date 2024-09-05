package org.example.service;

import org.example.db.ConnectionManager;
import org.example.db.SQLExecutor;
import org.example.viewmodel.LongestProject;
import org.example.viewmodel.MaxProjectCountClient;
import org.example.viewmodel.MaxSalaryWorker;
import org.example.viewmodel.ProjectPriceInfo;

import java.util.List;
import java.util.Optional;

public class DatabaseQueryService {
    private final ConnectionManager connectionManager;

    public DatabaseQueryService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public Optional<List<MaxSalaryWorker>> findMaxSalaryWorker(String sqlFilePath) {
        String errorMessage = "Failed to execute findMaxSalaryWorker query";

        try (SQLExecutor executor = new SQLExecutor(connectionManager.getConnection())) {
            return executor.executeQuery(
                    sqlFilePath,
                    errorMessage,
                    rs -> new MaxSalaryWorker(
                            rs.getString("name"),
                            rs.getInt("highest_salary")
                    )
            );
        }
    }

    public Optional<List<MaxProjectCountClient>> findMaxProjectsClient(String sqlFilePath) {
        String errorMessage = "Failed to execute findMaxProjectsClient query";

        try (SQLExecutor executor = new SQLExecutor(connectionManager.getConnection())) {
            return executor.executeQuery(
                    sqlFilePath,
                    errorMessage,
                    rs -> new MaxProjectCountClient(
                            rs.getString("client_name"),
                            rs.getInt("project_count")
                    )
            );
        }
    }

    public Optional<List<ProjectPriceInfo>> printProjectPrices(String sqlFilePath) {
        String errorMessage = "Failed to execute printProjectPrices query";

        try (SQLExecutor executor = new SQLExecutor(connectionManager.getConnection())) {
            return executor.executeQuery(
                    sqlFilePath,
                    errorMessage,
                    rs -> new ProjectPriceInfo(
                            rs.getString("project_name"),
                            rs.getInt("project_price")
                    )
            );
        }
    }

    public Optional<List<LongestProject>> findLongestProject (String sqlFilePath) {
        String errorMessage = "Failed to execute printProjectPrices query";

        try (SQLExecutor executor = new SQLExecutor(connectionManager.getConnection())) {
            return executor.executeQuery(
                    sqlFilePath,
                    errorMessage,
                    rs -> new LongestProject(
                            rs.getString("name"),
                            rs.getInt("duration_in_months")
                    )
            );
        }
    }
}