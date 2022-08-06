package com.onoprienko.dbclient.executor;

import com.onoprienko.dbclient.configuration.ConfigurationReader;
import com.onoprienko.dbclient.entity.DataBaseProperties;
import org.junit.jupiter.api.Test;
import org.postgresql.jdbc.PgResultSetMetaData;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QueryExecutorTest {
    String PROPERTIES_PATH = "./src/test/java/resources/configuration.properties";

    @Test
    void getTableColumnsReturnColumns() throws IOException, SQLException {
        DataBaseProperties dataBaseProperties = ConfigurationReader.readPropertiesFile(PROPERTIES_PATH);
        QueryExecutor queryExecutor = new QueryExecutor(dataBaseProperties);
        ResultSet resultSetMock = mock(ResultSet.class);
        PgResultSetMetaData pgResultSetMetaData = mock(PgResultSetMetaData.class);
        when(resultSetMock.getMetaData()).thenReturn(pgResultSetMetaData);
        when(pgResultSetMetaData.getColumnCount()).thenReturn(3);
        when(pgResultSetMetaData.getColumnName(1)).thenReturn("id");
        when(pgResultSetMetaData.getColumnName(2)).thenReturn("name");
        when(pgResultSetMetaData.getColumnName(3)).thenReturn("age");

        List<String> tableColumns = queryExecutor.getTableColumns(resultSetMock);
        assertEquals(tableColumns.get(0), "id");
        assertEquals(tableColumns.get(1), "name");
        assertEquals(tableColumns.get(2), "age");
    }

    @Test
    void getTableValuesReturnValues() throws SQLException, IOException {
        DataBaseProperties dataBaseProperties = ConfigurationReader.readPropertiesFile(PROPERTIES_PATH);
        QueryExecutor queryExecutor = new QueryExecutor(dataBaseProperties);
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSetMock.getObject("id")).thenReturn("1").thenReturn("2");
        when(resultSetMock.getObject("name")).thenReturn("tom").thenReturn("max");
        when(resultSetMock.getObject("age")).thenReturn("22").thenReturn("23");

        List<String> values = queryExecutor.getTableValues(resultSetMock, List.of("id", "name", "age"));
        assertEquals(values.get(0), "1");
        assertEquals(values.get(1), "tom");
        assertEquals(values.get(2), "22");
        assertEquals(values.get(3), "2");
        assertEquals(values.get(4), "max");
        assertEquals(values.get(5), "23");
    }
}