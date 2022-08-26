package com.onoprienko.dbclient.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReportResult {
    private String reportContent;
    private String fileName;

    public ReportResult(String reportContent) {
        this.reportContent = reportContent;
        String fileName = "report_" + LocalDateTime.now().toString().replace(":", "-");
        this.fileName = fileName.substring(0, fileName.indexOf(".")) + ".html";
    }
}
