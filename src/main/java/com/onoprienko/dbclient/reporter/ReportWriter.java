package com.onoprienko.dbclient.reporter;

import com.onoprienko.dbclient.entity.ReportData;
import com.onoprienko.dbclient.entity.ReportResult;

public interface ReportWriter {
    ReportResult writeReport(ReportData reportData);
}
