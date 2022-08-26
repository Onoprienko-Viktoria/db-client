package com.onoprienko.dbclient;

import com.onoprienko.dbclient.entity.ReportData;
import com.onoprienko.dbclient.entity.ReportResult;
import com.onoprienko.dbclient.executor.QueryExecutor;
import com.onoprienko.dbclient.reporter.ConsoleReportWriter;
import com.onoprienko.dbclient.reporter.HtmlReportWriter;
import com.onoprienko.dbclient.utils.FileWriter;
import com.onoprienko.dbclient.utils.PropertiesReader;
import com.onoprienko.server.Server;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

@Slf4j
public class Starter {
    private static final String DEFAULT_PROPERTIES_PATH = "./src/main/resources/configuration.properties";

    public static void main(String[] args) throws IOException {
        PropertiesReader propertiesReader = new PropertiesReader();
        Properties properties = propertiesReader.getProperties(DEFAULT_PROPERTIES_PATH);

        WebServerThread webServerThread = new WebServerThread();
        webServerThread.start();

        HtmlReportWriter htmlReportWriter = new HtmlReportWriter();
        ConsoleReportWriter consoleReportWriter = new ConsoleReportWriter();
        QueryExecutor queryExecutor = new QueryExecutor(properties);
        Scanner scanner = new Scanner(System.in);

        log.info("Db client start work");
        while (true) {
            System.out.println("Write query to execute: ");
            String query = scanner.nextLine();
            ReportData report = queryExecutor.execute(query);
            log.info("Query executed");
            consoleReportWriter.writeReport(report);
            if (!report.isUpdated()) {
                ReportResult reportResult = htmlReportWriter.writeReport(report);
                FileWriter.writeToFile(properties.getProperty("db.report.url"),
                        reportResult.getReportContent(), reportResult.getFileName());
            }
        }
    }

    public static class WebServerThread extends Thread {
        public void run() {
            PropertiesReader propertiesReader = new PropertiesReader();
            Properties properties = propertiesReader.getProperties(DEFAULT_PROPERTIES_PATH);
            Server server = new Server();
            server.setPort(5000);
            server.setWebappPath(properties.getProperty("db.report.url"));
            try {
                server.start();
            } catch (Exception e) {
                log.error("Exception on webserver ", e);
            }
        }
    }
}
