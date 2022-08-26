package com.onoprienko.dbclient.reporter;

import com.onoprienko.dbclient.entity.ReportData;
import com.onoprienko.dbclient.entity.ReportResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HtmlReportWriterTest {
    List<String> columns = List.of("id", "name", "age");
    List<String> variables = List.of("1", "tom", "12",
            "2", "Jake", "342",
            "3", "Marina", "21",
            "4", "Sabrina", "14");
    private final String RESULT = """
            <!doctype html>
            <html lang="en">
            <head>
                <meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
                      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

                <title>Reports</title>
            </head>
            <body>
                <h1 align="center">Query reports</h1>    <table class="table">        <thead><tr><th scope="col">id</th><th scope="col">name</th><th scope="col">age</th></tr>
                    </thead>
                    <tr></tr><tr><th scope="row">1</th><td>tom</td><td>12</td></tr><tr><th scope="row">2</th><td>Jake</td><td>342</td></tr><tr><th scope="row">3</th><td>Marina</td><td>21</td></tr><tr><th scope="row">4</th><td>Sabrina</td><td>14</td>    </tr>
                </tbody>
                </table>
            </body>
            </html>""";

    @Test
    void writeReportReturnCorrectHtmlReport() {
        ReportData reportData = new ReportData(false, columns, variables);
        HtmlReportWriter htmlReportWriter = new HtmlReportWriter();
        ReportResult reportResult = htmlReportWriter.writeReport(reportData);

        assertNotNull(reportResult);

        assertEquals(reportResult.getReportContent(), RESULT);
    }

    @Test
    void writeReportReturnNullIfQueryWasUpdated() {
        ReportData reportData = new ReportData(true, columns, variables);
        HtmlReportWriter htmlReportWriter = new HtmlReportWriter();
        ReportResult reportResult = htmlReportWriter.writeReport(reportData);

        assertNull(reportResult);
    }

    @Test
    void writeReportReturnNullIfColumnsAndVariablesNull() {
        ReportData reportData = new ReportData(true, null, null);
        HtmlReportWriter htmlReportWriter = new HtmlReportWriter();
        ReportResult reportResult = htmlReportWriter.writeReport(reportData);

        assertNull(reportResult);
    }
}