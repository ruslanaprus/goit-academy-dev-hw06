package org.example.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class SQLExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SQLExecutor.class);
    private final Connection connection;

    public SQLExecutor(Connection connection) {
        this.connection = connection;
    }

    /**
     * Executes SQL statements that modify the database (INSERT, UPDATE, DELETE, DDL).
     */
    public void executeUpdate(String sql) {
        logger.info("Executing SQL update...");
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            logger.info("SQL update executed successfully");
        } catch (SQLException e) {
            logger.error("Failed to execute SQL update", e);
            throw new RuntimeException("SQL update execution failed", e);
        }
    }

    /**
     * Executes a SQL SELECT statement that returns data.
     */
    public Optional<ResultSet> executeQuery(String sql) {
        logger.info("Executing SQL query...");
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return Optional.of(statement.executeQuery(sql));
        } catch (SQLException e) {
            logger.error("Failed to execute SQL query", e);
            return Optional.empty();
        }
    }

    /**
     * Executes multiple SQL statements as a batch.
     */
    public void executeBatch(String sql) {
        logger.info("Executing SQL batch...");
        String[] sqlStatements = sql.split(";");
        try (Statement statement = connection.createStatement()) {
            for (String sqlStatement : sqlStatements) {
                if (!sqlStatement.trim().isEmpty()) {
                    statement.addBatch(sqlStatement.trim());
                }
            }
            statement.executeBatch();
            logger.info("SQL batch executed successfully");
        } catch (SQLException e) {
            logger.error("Failed to execute SQL batch", e);
            throw new RuntimeException("SQL batch execution failed", e);
        }
    }

    /**
     * Close the connection.
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Connection closed successfully");
            }
        } catch (SQLException e) {
            logger.error("Failed to close the connection", e);
        }
    }
}