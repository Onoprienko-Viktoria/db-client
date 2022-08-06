package com.onoprienko.dbclient.entity;

import java.util.List;

public class ReportData {
    private boolean isUpdated;
    private int updatedCount;
    private List<String> columns;
    private List<String> values;

    public ReportData(boolean isUpdated, int updatedCount) {
        this.isUpdated = isUpdated;
        this.updatedCount = updatedCount;
    }

    public ReportData(boolean isUpdated,
                      List<String> columns, List<String> values) {
        this.isUpdated = isUpdated;
        this.columns = columns;
        this.values = values;
    }

    public int getUpdatedCount() {
        return updatedCount;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<String> getValues() {
        return values;
    }

    public boolean isUpdated() {
        return isUpdated;
    }
}
