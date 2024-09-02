package org.example.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.util.ConfigLoader;

import javax.sql.DataSource;

public class Postgresql implements Database {
    @Override
    public DataSource createDataSource(ConfigLoader configLoader) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(configLoader.getDbUrl());
        config.setUsername(configLoader.getDbUser());
        config.setPassword(configLoader.getDbPassword());

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        config.setConnectionTimeout(20_000);
        config.setIdleTimeout(300_000);
        config.setMaxLifetime(1_800_000);
        config.setMinimumIdle(1);
        config.setMaximumPoolSize(15);
        config.setInitializationFailTimeout(0);
        config.setLeakDetectionThreshold(2_000);

        return new HikariDataSource(config);
    }
}