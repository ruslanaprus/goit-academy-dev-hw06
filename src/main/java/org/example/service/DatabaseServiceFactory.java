package org.example.service;

import org.example.db.ConnectionManager;

public class DatabaseServiceFactory {
    public static DatabaseInitService createDatabaseInitService(ConnectionManager connectionManager) {
        return new DatabaseInitService(connectionManager);
    }

    public static DatabaseDropTableService createDatabaseDropTableService(ConnectionManager connectionManager) {
        return new DatabaseDropTableService(connectionManager);
    }

    public static DatabasePopulateService createDatabasePopulateService(ConnectionManager connectionManager) {
        return new DatabasePopulateService(connectionManager);
    }

    public static DatabaseQueryService createDatabaseQueryService(ConnectionManager connectionManager) {
        return new DatabaseQueryService(connectionManager);
    }
}