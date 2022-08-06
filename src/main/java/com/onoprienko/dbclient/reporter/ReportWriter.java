package com.onoprienko.dbclient.reporter;

import com.onoprienko.dbclient.entity.ReportData;

import java.io.*;
import java.util.List;
import java.util.UUID;

public class ReportWriter {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    private String reportPath;

    public ReportWriter() {

    }

    public ReportWriter(String reportPath) {
        this.reportPath = reportPath;
    }


    public String writeReport(ReportData reportData) {
        if (reportData == null) {
            System.out.println("Nothing to report");
        }else if (reportData.isUpdated()) {
            System.out.println("Updated: " + reportData.getUpdatedCount());
        } else {
            try {
                writeReportToConsole(reportData);
                if (reportPath != null) {
                    String path = writeReportToHtmlFile(reportData);
                    System.out.println("Write select report to file path: " + path);
                    return path;
                }
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Error while writing report: " + e + ANSI_RESET);
            }
        }
        return null;
    }

    void writeReportToConsole(ReportData reportData) {
        List<String> columns = reportData.getColumns();
        List<String> values = reportData.getValues();
        int columnCount = columns.size();

        StringBuilder stringBuilder = new StringBuilder("------------------------------------------------ \n");
        for (String column : columns) {
            stringBuilder.append(String.format("%-15s", column));
        }
        for (int i = 0; i < values.size(); i++) {
            if (i % columnCount == 0) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(String.format("%-15s", values.get(i)));
            i++;
        }
        stringBuilder.append("\n------------------------------------------------");
        System.out.println("Table report: ");
        System.out.println(ANSI_GREEN + stringBuilder + ANSI_RESET);
    }

    String writeReportToHtmlFile(ReportData reportData) throws IOException {
        List<String> columns = reportData.getColumns();
        List<String> values = reportData.getValues();
        StringBuilder stringBuilder = new StringBuilder("<table>\n  <tr>");
        for (String column : columns) {
            stringBuilder.append("<th>").append(column).append("</th>");
        }
        int columnCount = columns.size();
        for (int i = 0; i < values.size(); i++) {
            if (i % columnCount == 0) {
                stringBuilder.append("  </tr>\n <tr>");
            }
            stringBuilder.append("<td>" + values.get(i) + "</td>");
            i++;
        }
        stringBuilder.append("</table>");
        String report = stringBuilder.toString();
        File reportFile = new File(reportPath, "report" + UUID.randomUUID() + ".html");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(reportFile)))) {
            bufferedWriter.write(report);
            return reportFile.getAbsolutePath();
        }
    }

}
