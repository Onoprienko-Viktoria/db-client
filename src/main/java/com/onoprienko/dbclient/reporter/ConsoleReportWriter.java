package com.onoprienko.dbclient.reporter;

import com.onoprienko.dbclient.entity.ReportData;
import com.onoprienko.dbclient.entity.ReportResult;
import dnl.utils.text.table.TextTable;

import java.util.List;

public class ConsoleReportWriter implements ReportWriter {

    public ReportResult writeReport(ReportData reportData) {
        if (reportData.isUpdated()) {
            return writeUpdateReport(reportData);
        } else {
            return writeSelectReport(reportData);
        }
    }

    ReportResult writeUpdateReport(ReportData reportData) {
        String reportContent = "Updated: " + reportData.getUpdatedCount();
        System.out.println(reportContent);
        return new ReportResult(reportContent);
    }

    ReportResult writeSelectReport(ReportData reportData) {
        String[] columns = new String[reportData.getColumns().size()];
        reportData.getColumns().toArray(columns);
        List<String> values = reportData.getValues();
        Object[][] data = extractToDoubleArray(values, columns);
        TextTable textTable = new TextTable(columns, data);
        textTable.printTable();
        return new ReportResult(textTable.toString());
    }

    Object[][] extractToDoubleArray(List<String> values, String[] columns) {
        Object[][] data = new Object[values.size() / columns.length][values.size()];
        int columnsCounter = 0;
        int valuesCounter = 0;
        for (String value : values) {
            if (valuesCounter % columns.length == 0 && valuesCounter != 0) {
                columnsCounter++;
                valuesCounter = 0;
            }
            data[columnsCounter][valuesCounter] = value;
            valuesCounter++;
        }
        return data;
    }
}