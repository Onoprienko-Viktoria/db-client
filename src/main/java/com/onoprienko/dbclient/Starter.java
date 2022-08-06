package com.onoprienko.dbclient;

import com.onoprienko.dbclient.configuration.ConfigurationReader;
import com.onoprienko.dbclient.entity.DataBaseProperties;
import com.onoprienko.dbclient.entity.ReportData;
import com.onoprienko.dbclient.executor.QueryExecutor;
import com.onoprienko.dbclient.reporter.ReportWriter;

import java.io.IOException;
import java.util.Scanner;

public class Starter {
    private static final String DEFAULT_PROPERTIES_PATH = "./src/main/resources/configuration.properties";

    public static void main(String[] args) throws IOException {
        DataBaseProperties dataBaseProperties = ConfigurationReader.readPropertiesFile(DEFAULT_PROPERTIES_PATH);

        ReportWriter reportWriter = new ReportWriter(dataBaseProperties.getReportPath());
        QueryExecutor queryExecutor = new QueryExecutor(dataBaseProperties);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Db client start work");
        while (true) {
            System.out.println("Write query to execute: ");
            String query = scanner.nextLine();
            ReportData report = queryExecutor.execute(query);
            reportWriter.writeReport(report);
        }
    }
}
