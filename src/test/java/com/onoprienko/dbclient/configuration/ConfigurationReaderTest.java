package com.onoprienko.dbclient.configuration;

import com.onoprienko.dbclient.entity.DataBaseProperties;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationReaderTest {
    @Test
    public void readPropertiesFileReturnCorrectProperties() throws IOException {
        String PROPERTIES_PATH = "./src/test/java/resources/configuration.properties";
        DataBaseProperties dataBaseProperties = ConfigurationReader.readPropertiesFile(PROPERTIES_PATH);

        assertNotNull(dataBaseProperties);

        String dbUrl = "jdbc:postgresql://localhost:5433/testdb";
        assertEquals(dbUrl, dataBaseProperties.getDbUrl());

        String user = "user";
        assertEquals(user, dataBaseProperties.getUser());

        String password = "pass";
        assertEquals(password, dataBaseProperties.getPassword());

        String reportUrl = "./src/test/java/resources/reports";
        assertEquals(reportUrl, dataBaseProperties.getReportPath());
    }


    @Test
    public void readPropertiesFromFileThatDontExistReturnError() {
        assertThrows(FileNotFoundException.class, () -> ConfigurationReader.readPropertiesFile("/src/main"));
    }

    @Test
    public void readPropertiesFromEmptyFile() throws IOException {
        String EMPTY_PROPERTIES_PATH = "./src/test/java/resources/empty_configuration.properties";
        DataBaseProperties dataBaseProperties = ConfigurationReader.readPropertiesFile(EMPTY_PROPERTIES_PATH);

        assertNull(dataBaseProperties.getDbUrl());
        assertNull(dataBaseProperties.getUser());
        assertNull(dataBaseProperties.getPassword());
        assertNull(dataBaseProperties.getReportPath());
    }

    @Test
    public void readPropertiesFromHalfEmptyFile() throws IOException {
        String HALF_EMPTY_PROPERTIES_PATH = "./src/test/java/resources/half_empty_configuration.properties";
        DataBaseProperties dataBaseProperties = ConfigurationReader.readPropertiesFile(HALF_EMPTY_PROPERTIES_PATH);

        assertNull(dataBaseProperties.getDbUrl());
        assertNull(dataBaseProperties.getUser());

        String password = "pass";
        assertEquals(password, dataBaseProperties.getPassword());

        String reportUrl = "./src/test/java/resources/reports";
        assertEquals(reportUrl, dataBaseProperties.getReportPath());
    }


}