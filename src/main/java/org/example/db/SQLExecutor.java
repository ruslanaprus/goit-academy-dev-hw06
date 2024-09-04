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
        logger.info("Trying to execute SQL script...");

        String[] sqlStatements = sql.split(";");

        try (Statement statement = connection.createStatement()) {
            for (String sqlStatement : sqlStatements) {
                sqlStatement = sqlStatement.trim();
                if (!sqlStatement.isEmpty()) {
                    logger.info("Executing SQL: {}", sqlStatement);
                    statement.execute(sqlStatement);
                }
            }
            logger.info("SQL script executed successfully");
        } catch (SQLException e) {
            logger.error("Failed to execute SQL script", e);
        }
    }

    public void executeSQLBatch(String sql) {
        logger.info("Trying to execute SQL script...");

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
            logger.info("SQL script executed successfully");
        } catch (SQLException e) {
            logger.error("Failed to execute SQL script", e);
        }
    }
}