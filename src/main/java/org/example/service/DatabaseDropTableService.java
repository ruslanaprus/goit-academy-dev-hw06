package org.example.service;

import org.example.db.ConnectionManager;
import org.example.db.SQLExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDropTableService {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseDropTableService.class);
    private final ConnectionManager connectionManager;

    public DatabaseDropTableService(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void dropTable(String tableName) {
        String dropTableSQL = "DROP TABLE IF EXISTS " + tableName + " CASCADE";
        try (Connection connection = connectionManager.getConnection()) {
            SQLExecutor executor = new SQLExecutor(connection);
            executor.executeSQL(dropTableSQL);
            logger.info("Table '{}' dropped successfully.", tableName);
        } catch (SQLException e) {
            logger.error("SQL error occurred while dropping table '{}': {}", tableName, e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while dropping table '{}': {}", tableName, e.getMessage(), e);
        }
    }

    public void dropAllTables() {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(
                    "SELECT tablename FROM pg_tables WHERE schemaname = 'public'");

            while (resultSet.next()) {
                String tableName = resultSet.getString("tablename");
                dropTable(connection, tableName);
            }
        } catch (Exception e) {
            logger.error("Failed to drop all tables", e);
        } finally {
            connectionManager.close();
        }
    }

    private void dropTable(Connection connection, String tableName) {
        String dropTableSQL = "DROP TABLE IF EXISTS " + tableName + " CASCADE";
        try {
            SQLExecutor executor = new SQLExecutor(connection);
            executor.executeSQL(dropTableSQL);
            logger.info("Table '{}' dropped successfully.", tableName);
        } catch (Exception e) {
            logger.error("Failed to drop table '{}'", tableName, e);
        }
    }
}
