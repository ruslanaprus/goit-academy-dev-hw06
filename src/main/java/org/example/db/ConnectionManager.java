package org.example.db;

import org.example.util.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    private Database database;
    private Connection connection;

    public ConnectionManager(Database database) {
        this.database = database;
        initDataSource();
    }

    private void initDataSource() {
        try {
            ConfigLoader configLoader = new ConfigLoader();
            this.connection = database.createDataSource(configLoader).getConnection();
        } catch (SQLException e) {
            logger.error("Failed to establish a database connection", e);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Failed to close database connection", e);
            }
        }
    }
}