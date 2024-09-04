package org.example.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        logger.info("Trying to execute SQL update...");

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            logger.info("SQL update executed successfully");
        } catch (SQLException e) {
            logger.error("Failed to execute SQL update", e);
        }
    }

    /**
     * Executes a SQL SELECT statement that returns data.
     */
    public ResultSet executeQuery(String sql) {
        logger.info("Trying to execute SQL query...");

        try (Statement statement = connection.createStatement()) {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            logger.error("Failed to execute SQL query", e);
            return null;
        }
    }

    /**
     * Executes multiple SQL statements as a batch.
     */
    public void executeBatch(String sql) {
        logger.info("Trying to execute SQL batch...");

        String[] sqlStatements = sql.split(";");

        try (Statement statement = connection.createStatement()) {
            for (String sqlStatement : sqlStatements) {
                sqlStatement = sqlStatement.trim();
                if (!sqlStatement.isEmpty()) {
                    logger.info("Adding to batch: {}", sqlStatement);
                    statement.addBatch(sqlStatement);
                }
            }
            statement.executeBatch();
            logger.info("SQL batch executed successfully");
        } catch (SQLException e) {
            logger.error("Failed to execute SQL batch", e);
        }
    }
}