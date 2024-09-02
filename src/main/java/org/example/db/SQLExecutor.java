package org.example.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SQLExecutor.class);
    private final Connection connection;

    public SQLExecutor(Connection connection) {
        this.connection = connection;
    }

    public void executeSQL(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            logger.info("SQL executed successfully");
        } catch (SQLException e) {
            logger.error("Failed to execute SQL", e);
        }
    }
}