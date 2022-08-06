package com.onoprienko.dbclient.entity;

public class DataBaseProperties {
    private final String dbUrl;
    private final String reportPath;
    private final String user;
    private final String password;


    public DataBaseProperties(String dbUrl, String reportPath, String user, String password) {
        this.dbUrl = dbUrl;
        this.reportPath = reportPath;
        this.user = user;
        this.password = password;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getPassword() {
        return password;
    }

    public String getReportPath() {
        return reportPath;
    }

    public String getUser() {
        return user;
    }
}
