package org.example.service;

import org.example.db.ConnectionManager;
import org.example.db.Database;

public class DatabaseServiceFactory {
    public static DatabaseInitService createDatabaseInitService(Database database) {
        ConnectionManager connectionManager = new ConnectionManager(database);
        return new DatabaseInitService(connectionManager);
    }

    public static DatabaseDropTableService createDatabaseDropTableService(Database database) {
        ConnectionManager connectionManager = new ConnectionManager(database);
        return new DatabaseDropTableService(connectionManager);
    }

    public static DatabasePopulateService createDatabasePopulateService(Database database) {
        ConnectionManager connectionManager = new ConnectionManager(database);
        return new DatabasePopulateService(connectionManager);
    }

    //TODO Other services like DatabasePopulateService and DatabaseQueryService will be created similarly.
}