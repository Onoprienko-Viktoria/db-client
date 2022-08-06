package com.onoprienko.dbclient.reporter;

import com.onoprienko.dbclient.entity.ReportData;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportWriterTest {
    private static final String REPORT_PATH = "./src/test/java/resources/reports";
    private final List<String> columns = List.of("id", "name", "age");
    private final List<String> values = List.of("1", "Alex", "20",
            "2", "Jane", "15",
            "3", "Max", "33",
            "4", "Tom", "19");
    private final ReportData reportDataForSelectCommand = new ReportData(false, columns, values);
    private final ReportData reportDataForUpdateCommand = new ReportData(true, 12);

    @Test
    void writeReportOnSelectCommand() {
        ReportWriter reportWriter = new ReportWriter(REPORT_PATH);
        try (java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            String reportPath = reportWriter.writeReport(reportDataForSelectCommand);
            String outputContent = output.toString();
            String readContent = readContent(reportPath);
            assertEquals(readContent, "<table>\n" +
                    "  <tr><th>id</th><th>name</th><th>age</th>  </tr>\n" +
                    " <tr><td>1</td><td>20</td><td>Jane</td>  </tr>\n" +
                    " <tr><td>3</td><td>33</td><td>Tom</td></table>");
            assertEquals(outputContent, "Table report: \n" +
                    "\u001B[32m------------------------------------------------ \n" +
                    "id             name           age            \n" +
                    "1              20             Jane           \n" +
                    "3              33             Tom            \n" +
                    "------------------------------------------------\u001B[0m\n" +
                    "Write select report to file path: " + reportPath + "\r\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void writeReportOnUpdateCommand() {
        ReportWriter reportWriter = new ReportWriter(REPORT_PATH);
        try (java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            reportWriter.writeReport(reportDataForUpdateCommand);
            String outputContent = output.toString();
            assertEquals(outputContent, "Updated: 12\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void writeReportToConsole() {
        ReportWriter reportWriter = new ReportWriter();
        try (java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            reportWriter.writeReport(reportDataForUpdateCommand);
            String outputContent = output.toString();
            assertEquals("Updated: 12\r\n", outputContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void writeReportToWithNonExistReportPath() {
        ReportWriter reportWriter = new ReportWriter("/dnifnai/dfs");
        try (java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream()) {
            System.setOut(new PrintStream(output));
            String reportPath = reportWriter.writeReport(reportDataForSelectCommand);
            String outputContent = output.toString();
            assertTrue(outputContent.contains("Error while writing report: java.io.FileNotFoundException:"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void writeReportToConsoleWithNullRequest() {
        ReportWriter reportWriter = new ReportWriter();
        assertThrows(NullPointerException.class, () -> reportWriter.writeReportToConsole(null));
    }

    @Test
    void writeReportWithNullRequest() {
        ReportWriter reportWriter = new ReportWriter();
        assertThrows(NullPointerException.class, () -> reportWriter.writeReport(null));
    }

    @Test
    void writeReportToHtmlWithNullRequest() {
        ReportWriter reportWriter = new ReportWriter();
        assertThrows(NullPointerException.class, () -> reportWriter.writeReportToHtmlFile(null));
    }

    @Test
    void writeReportToHtmlFileWriteCorrectContent() throws IOException {
        ReportWriter reportWriter = new ReportWriter(REPORT_PATH);
        String reportPath = reportWriter.writeReportToHtmlFile(reportDataForSelectCommand);
        String readContent = readContent(reportPath);
        assertEquals(readContent, "<table>\n" +
                "  <tr><th>id</th><th>name</th><th>age</th>  </tr>\n" +
                " <tr><td>1</td><td>20</td><td>Jane</td>  </tr>\n" +
                " <tr><td>3</td><td>33</td><td>Tom</td></table>");
    }


    private String readContent(String path) throws IOException {
        File file = new File(path);
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] text = new byte[(int) file.length()];
            inputStream.read(text);
            return new String(text);
        }
    }
}