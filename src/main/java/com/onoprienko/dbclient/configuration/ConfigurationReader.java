package com.onoprienko.dbclient.configuration;

import com.onoprienko.dbclient.entity.DataBaseProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {

    public static DataBaseProperties readPropertiesFile(String path) throws IOException {
        try (InputStream input = new FileInputStream(path)) {
            Properties properties = new Properties();
            properties.load(input);
            String dbUrl = properties.getProperty("db.url");
            String reportUrl = properties.getProperty("db.report.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            return new DataBaseProperties(dbUrl, reportUrl, user, password);
        }
    }
}
