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
import java.util.Optional;

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

        Optional<List<MaxSalaryWorker>> maxSalaryWorkers = queryService.findMaxSalaryWorker(FIND_MAX_SALARY_WORKER_SQL);
        maxSalaryWorkers.ifPresentOrElse(
                workers -> {
                    logger.info("MaxSalaryWorker(s) found: {}", workers.size());
                    for (MaxSalaryWorker worker : workers) {
                        logger.info(worker.toString());
                    }
                },
                () -> logger.warn("No MaxSalaryWorker(s) found.")
        );

        Optional<List<MaxProjectCountClient>> maxProjectCountClients = queryService.findMaxProjectsClient(FIND_MAX_PROJECT_CLIENT_SQL);
        maxProjectCountClients.ifPresentOrElse(
                clients -> {
                    logger.info("MaxProjectCountClient(s) found: {}", clients.size());
                    for (MaxProjectCountClient client : clients) {
                        logger.info(client.toString());
                    }
                },
                () -> logger.warn("No MaxProjectCountClient(s) found.")
        );

        Optional<List<ProjectPriceInfo>> projectPriceInfos = queryService.printProjectPrices(PRINT_PROJECT_PRICES_SQL);
        projectPriceInfos.ifPresentOrElse(
                infos -> {
                    logger.info("ProjectPriceInfo(s) found: {}", infos.size());
                    for (ProjectPriceInfo info : infos) {
                        logger.info(info.toString());
                    }
                },
                () -> logger.warn("No ProjectPriceInfo(s) found.")
        );
    }
}