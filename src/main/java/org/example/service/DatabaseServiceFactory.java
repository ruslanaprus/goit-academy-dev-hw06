package org.example.service;

import org.example.db.ConnectionManager;
import org.example.db.Database;

public class DatabaseServiceFactory {
    public static DatabaseInitService createDatabaseInitService(Database database) {
        ConnectionManager connectionManager = new ConnectionManager(database);
        return new DatabaseInitService(connectionManager);
    }

    //TODO Other services like DatabasePopulateService and DatabaseQueryService will be created similarly.
}