package com.onoprienko.dbclient.executor;

import com.onoprienko.dbclient.entity.DataBaseProperties;
import com.onoprienko.dbclient.entity.ReportData;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class QueryExecutor {
    private final DataBaseProperties dataBaseProperties;

    public QueryExecutor(Properties properties) {
        this.dataBaseProperties = new DataBaseProperties(properties.getProperty("db.url"),
                properties.getProperty("db.user"), properties.getProperty("db.password"));
        try (Connection connection = DriverManager.getConnection(dataBaseProperties.getDbUrl(),
                dataBaseProperties.getUser(), dataBaseProperties.getPassword())) {
            log.info("Create data base connection");
        } catch (Exception e) {
            log.error("Can not connect to data base, ", e);
            throw new RuntimeException(e);
        }
    }

    public ReportData execute(String query) {
        try {
            String SELECT_COMMAND = "SELECT";
            if (query.startsWith(SELECT_COMMAND)) {
                return executeQuery(query);
            }
            return executeUpdate(query);
        } catch (SQLException e) {
            log.error("Exception while execute query: ", e);
            return null;
        }
    }

    ReportData executeQuery(String query) throws SQLException {
        try (ResultSet resultSet = getConnection().createStatement().executeQuery(query)) {
            List<String> columns = getTableColumns(resultSet);
            List<String> values = getTableValues(resultSet, columns);
            return new ReportData(false, columns, values);
        }
    }

    ReportData executeUpdate(String query) throws SQLException {
        int changedRows = getConnection().createStatement().executeUpdate(query);
        return new ReportData(true, changedRows);
    }

    List<String> getTableColumns(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<String> columns = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            columns.add(metaData.getColumnName(i));
        }
        return columns;
    }

    List<String> getTableValues(ResultSet resultSet, List<String> columns) throws SQLException {
        List<String> values = new ArrayList<>();
        while (resultSet.next()) {
            for (String column : columns) {
                values.add(String.valueOf(resultSet.getObject(column)));
            }
        }
        return values;
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dataBaseProperties.getDbUrl(), dataBaseProperties.getUser(),
                dataBaseProperties.getPassword());
    }
}
