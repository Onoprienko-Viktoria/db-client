package com.onoprienko.dbclient.reporter;

import com.onoprienko.dbclient.entity.ReportData;
import com.onoprienko.dbclient.entity.ReportResult;
import lombok.extern.slf4j.Slf4j;
import org.owasp.encoder.Encode;

import java.util.List;

@Slf4j
public class HtmlReportWriter implements ReportWriter {
    private final static String HTML_TEMPLATE_START = """
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
                <h1 align="center">Query reports</h1>    <table class="table">        <thead>""";
    private final static String HTML_TEMPLATE_MIDDLE = """
            </tr>
                    </thead>
                    <tr>""";
    private final static String HTML_TEMPLATE_END = """
                </tr>
                </tbody>
                </table>
            </body>
            </html>""";


    public ReportResult writeReport(ReportData reportData) {
        if (reportData.isUpdated()) {
            log.info("Nothing to write in html report");
            return null;
        }
        List<String> columns = reportData.getColumns();
        List<String> values = reportData.getValues();
        StringBuilder stringBuilder = new StringBuilder(HTML_TEMPLATE_START).append("<tr>");
        for (String column : columns) {
            stringBuilder.append("<th scope=\"col\">").append(Encode.forHtml(column)).append("</th>");
        }
        stringBuilder.append(HTML_TEMPLATE_MIDDLE);
        int countRows = 0;
        for (String value : values) {
            if (countRows % columns.size() == 0) {
                stringBuilder.append("</tr><tr><th scope=\"row\">").append(Encode.forHtml(value)).append("</th>");
            } else {
                stringBuilder.append("<td>").append(Encode.forHtml(value)).append("</td>");
            }
            countRows++;
        }
        stringBuilder.append(HTML_TEMPLATE_END);
        return new ReportResult(stringBuilder.toString());
    }
}

