package org.example;

import org.example.db.ConnectionManager;
import org.example.db.Database;
import org.example.db.Postgresql;
import org.example.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.constants.Constants.*;

public class AppLauncher {
    private static final Logger logger = LoggerFactory.getLogger(AppLauncher.class);

    public static void main(String[] args) {
        Database postgresql = new Postgresql();
        ConnectionManager connectionManager = ConnectionManager.getInstance(postgresql);

        DatabaseDropTableService dropTableService = DatabaseServiceFactory.createDatabaseDropTableService(connectionManager);
        dropTableService.dropAllTables();

        DatabaseInitService initService = DatabaseServiceFactory.createDatabaseInitService(connectionManager);
        initService.initializeDatabase(INIT_DB_SQL);

        DatabasePopulateService populateService = DatabaseServiceFactory.createDatabasePopulateService(connectionManager);
        populateService.insertData(POPULATE_DB_SQL);

        DatabaseQueryService queryService = DatabaseServiceFactory.createDatabaseQueryService(connectionManager);
        queryService.findMaxSalaryWorker(FIND_MAX_SALARY_WORKER_SQL).ifPresent(workers -> {
            logger.info("MaxSalaryWorker(s) found: {}", workers.size());
            workers.forEach(worker -> logger.info(worker.toString()));
        });

        queryService.findMaxProjectsClient(FIND_MAX_PROJECT_CLIENT_SQL).ifPresent(clients -> {
            logger.info("MaxProjectCountClient(s) found: {}", clients.size());
            clients.forEach(client -> logger.info(client.toString()));
        });

        queryService.printProjectPrices(PRINT_PROJECT_PRICES_SQL).ifPresent(projects -> {
            logger.info("ProjectPriceInfo(s) found: {}", projects.size());
            projects.forEach(project -> logger.info(project.toString()));
        });

        queryService.findLongestProject(FIND_LONGEST_PROJECT_SQL).ifPresent(projects -> {
            logger.info("LongestProject(s) found: {}", projects.size());
            projects.forEach(project -> logger.info(project.toString()));
        });

        queryService.findYoungestEldestWorker(FIND_YOUNGEST_ELDEST_SQL).ifPresent(workers -> {
            logger.info("YoungestEldestWorker(s) found: {}", workers.size());
            workers.forEach(worker -> logger.info(worker.toString()));
        });
    }
}