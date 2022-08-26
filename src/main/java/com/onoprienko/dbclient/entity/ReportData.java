package com.onoprienko.dbclient.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class ReportData {
    private final boolean isUpdated;
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
}
