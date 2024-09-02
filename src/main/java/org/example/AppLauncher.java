package org.example;

import org.example.db.Database;
import org.example.db.Postgresql;
import org.example.service.DatabaseDropTableService;
import org.example.service.DatabaseInitService;
import org.example.service.DatabaseServiceFactory;

import static org.example.constants.Constants.SOURCE_FILE;

public class AppLauncher {
    public static void main(String[] args) {
        Database postgresql = new Postgresql();

//        DatabaseDropTableService dropTableService = DatabaseServiceFactory.createDatabaseDropTableService(postgresql);
//        dropTableService.dropAllTables();

        DatabaseInitService initService = DatabaseServiceFactory.createDatabaseInitService(postgresql);
        initService.initializeDatabase(SOURCE_FILE);
    }
}