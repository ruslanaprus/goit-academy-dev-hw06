package org.example;

import org.example.db.Database;
import org.example.db.Postgresql;
import org.example.viewmodel.MaxProjectCountClient;
import org.example.viewmodel.MaxSalaryWorker;
import org.example.service.*;
import org.example.viewmodel.ProjectPriceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.example.constants.Constants.*;

public class AppLauncher {
    private static final Logger logger = LoggerFactory.getLogger(AppLauncher.class);

    public static void main(String[] args) {
        Database postgresql = new Postgresql();

        DatabaseDropTableService dropTableService = DatabaseServiceFactory.createDatabaseDropTableService(postgresql);
        dropTableService.dropAllTables();

        DatabaseInitService initService = DatabaseServiceFactory.createDatabaseInitService(postgresql);
        initService.initializeDatabase(INIT_DB_SQL);

        DatabasePopulateService populateService = DatabaseServiceFactory.createDatabasePopulateService(postgresql);
        populateService.insertData(POPULATE_DB_SQL);

        DatabaseQueryService queryService = DatabaseServiceFactory.createDatabaseQueryService(postgresql);

        List<MaxSalaryWorker> maxSalaryWorkers = queryService.findMaxSalaryWorker(FIND_MAX_SALARY_WORKER_SQL);

        logger.info("MaxSalaryWorker(s): {}", maxSalaryWorkers.size());
        for (MaxSalaryWorker worker : maxSalaryWorkers) {
            logger.info(worker.toString());
        }

        DatabaseQueryService queryService1 = DatabaseServiceFactory.createDatabaseQueryService(postgresql);

        List<MaxProjectCountClient> maxProjectCountClients = queryService1.findMaxProjectsClient(FIND_MAX_PROJECT_CLIENT_SQL);

        logger.info("MaxProjectCountClient(s): {}", maxProjectCountClients.size());
        for (MaxProjectCountClient client : maxProjectCountClients) {
            logger.info(client.toString());
        }

        DatabaseQueryService queryService2 = DatabaseServiceFactory.createDatabaseQueryService(postgresql);

        List<ProjectPriceInfo> projectPriceInfos = queryService2.printProjectPrices(PRINT_PROJECT_PRICES_SQL);

        logger.info("ProjectPriceInfo(s): {}", projectPriceInfos.size());
        for (ProjectPriceInfo info : projectPriceInfos) {
            logger.info(info.toString());
        }

    }
}