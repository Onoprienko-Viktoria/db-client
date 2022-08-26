package com.onoprienko.dbclient.reporter;

import com.onoprienko.dbclient.entity.ReportData;
import com.onoprienko.dbclient.entity.ReportResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleReportWriterTest {
    List<String> columns = List.of("id", "name", "age");
    List<String> variables = List.of("1", "tom", "12",
            "2", "Jake", "342",
            "3", "Marina", "21",
            "4", "Sabrina", "14");
    private final String RESULT = """
            __________________
            | id| name   | age|
            |=================|
            | 1 | tom    | 12 |
            | 2 | Jake   | 342|
            | 3 | Marina | 21 |
            | 4 | Sabrina| 14 |\r\n""";

    @Test
    void writeReportToConsoleOnSelectCommand() throws IOException {
        ReportData reportData = new ReportData(false, columns, variables);
        ConsoleReportWriter consoleReportWriter = new ConsoleReportWriter();
        try (java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            ReportResult reportResult = consoleReportWriter.writeReport(reportData);
            String resultOutput = output.toString();

            assertNotNull(reportResult.getReportContent());

            assertEquals(resultOutput, RESULT);
        }
    }

    @Test
    void writeReportReturnExceptionIfColumnsNull() {
        ReportData reportData = new ReportData(false, null, variables);
        ConsoleReportWriter consoleReportWriter = new ConsoleReportWriter();
        assertThrows(NullPointerException.class, () -> consoleReportWriter.writeReport(reportData));
    }

    @Test
    void writeReportReturnExceptionIfVariablesNull() {
        ReportData reportData = new ReportData(false, columns, null);
        ConsoleReportWriter consoleReportWriter = new ConsoleReportWriter();
        assertThrows(NullPointerException.class, () -> consoleReportWriter.writeReport(reportData));
    }

    @Test
    void writeReportToConsoleOnUpdateCommand() throws IOException {
        ReportData reportData = new ReportData(true, 21);
        ConsoleReportWriter consoleReportWriter = new ConsoleReportWriter();
        try (java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            ReportResult reportResult = consoleReportWriter.writeReport(reportData);
            String resultOutput = output.toString();

            assertNotNull(reportResult.getReportContent());

            assertEquals(resultOutput, "Updated: 21\r\n");
        }
    }

    @Test
    void writeUpdateReport() throws IOException {
        ReportData reportData = new ReportData(true, 123);
        ConsoleReportWriter consoleReportWriter = new ConsoleReportWriter();
        try (java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            ReportResult reportResult = consoleReportWriter.writeUpdateReport(reportData);
            String resultOutput = output.toString();

            assertNotNull(reportResult.getReportContent());

            assertEquals(resultOutput, "Updated: 123\r\n");
        }
    }

    @Test
    void extractToDoubleArray() {
        ConsoleReportWriter consoleReportWriter = new ConsoleReportWriter();
        String[] columnsArray = new String[columns.size()];
        columns.toArray(columnsArray);
        Object[][] objects = consoleReportWriter.extractToDoubleArray(variables, columnsArray);
        assertNotNull(objects);
    }
}