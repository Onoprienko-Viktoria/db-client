package com.onoprienko.dbclient.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class PropertiesReader {
    private Map<String, Properties> propertiesMap = new ConcurrentHashMap<>();

    public Properties getProperties(String path) {
        if (!propertiesMap.containsKey(path)) {
            propertiesMap.put(path, readPropertiesFile(path));
        }
        return propertiesMap.get(path);
    }

    public static Properties readPropertiesFile(String path) {
        try (InputStream input = new FileInputStream(path)) {
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (Exception e) {
            log.error("Can not read properties from path {}, error {}", path, e);
            throw new RuntimeException(e);
        }
    }
}
