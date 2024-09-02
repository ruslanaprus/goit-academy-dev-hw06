package org.example.db;

import org.example.util.ConfigLoader;

import javax.sql.DataSource;

public interface Database {
    DataSource createDataSource(ConfigLoader configLoader);
}