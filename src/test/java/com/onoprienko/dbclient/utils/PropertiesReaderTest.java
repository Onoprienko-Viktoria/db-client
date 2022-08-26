package com.onoprienko.dbclient.utils;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesReaderTest {
    @Test
    public void readPropertiesFileReturnCorrectProperties() throws IOException {
        String PROPERTIES_PATH = "./src/test/java/resources/configuration.properties";
        PropertiesReader propertiesReader = new PropertiesReader();
        Properties properties = propertiesReader.getProperties(PROPERTIES_PATH);

        assertNotNull(properties);

        String dbUrl = "jdbc:postgresql://localhost:5433/testdb";
        assertEquals(dbUrl, properties.getProperty("db.url"));

        String user = "user";
        assertEquals(user, properties.getProperty("db.user"));

        String password = "pass";
        assertEquals(password, properties.getProperty("db.password"));

        String reportUrl = "./src/test/java/resources/reports";
        assertEquals(reportUrl, properties.getProperty("db.report.url"));
    }


    @Test
    public void readPropertiesFromFileThatDontExistReturnError() {
        PropertiesReader propertiesReader = new PropertiesReader();
        assertThrows(RuntimeException.class, () -> propertiesReader.getProperties("/src/main"));
    }

    @Test
    public void readPropertiesFromEmptyFile() {
        String EMPTY_PROPERTIES_PATH = "./src/test/java/resources/empty_configuration.properties";
        PropertiesReader propertiesReader = new PropertiesReader();
        Properties properties = propertiesReader.getProperties(EMPTY_PROPERTIES_PATH);

        assertNull(properties.getProperty("db.url"));
        assertNull(properties.getProperty("db.user"));
        assertNull(properties.getProperty("db.password"));
        assertNull(properties.getProperty("db.report.url"));
    }

    @Test
    public void readPropertiesFromHalfEmptyFile() {
        String HALF_EMPTY_PROPERTIES_PATH = "./src/test/java/resources/half_empty_configuration.properties";
        PropertiesReader propertiesReader = new PropertiesReader();
        Properties properties = propertiesReader.getProperties(HALF_EMPTY_PROPERTIES_PATH);

        assertNull(properties.getProperty("db.url"));
        assertNull(properties.getProperty("db.user"));

        String password = "pass";
        assertEquals(password, properties.getProperty("db.password"));

        String reportUrl = "./src/test/java/resources/reports";
        assertEquals(reportUrl, properties.getProperty("db.report.url"));
    }


}