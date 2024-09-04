package org.example.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLExecutor implements AutoCloseable {
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

    // TODO wrap in Optional for potential null values
    public <T> List<T> executeQuery(String sqlFilePath, String errorMessage, ResultSetMapper<T> mapper) {
        List<T> result = new ArrayList<>();

        Path path = Paths.get(sqlFilePath);
        try {
            String sql = new String(Files.readAllBytes(path));

            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    result.add(mapper.map(rs));
                }
            }
        } catch (SQLException | IOException e) {
            logger.error(errorMessage, e);
        }

        return result;
    }

    @FunctionalInterface
    public interface ResultSetMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }

    @Override
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