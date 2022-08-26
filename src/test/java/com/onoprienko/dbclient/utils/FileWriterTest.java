package com.onoprienko.dbclient.utils;

import com.onoprienko.dbclient.entity.ReportResult;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileWriterTest {

    @Test
    void writeToFileWriteCorrectContentToHtmlFile() throws IOException {
        String path = "./src/test/java/resources/reports";
        String content = "<!doctype html>\n" +
                "            <html lang=\"en\">\n" +
                "            <head>\n" +
                "                <meta charset=\"utf-8\">\n" +
                "                <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "                <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n" +
                "                      integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\n" +
                "\n" +
                "                <title>Reports</title>\n" +
                "            </head>";
        ReportResult reportResult = new ReportResult(content);

        assertNotNull(reportResult.getFileName());
        FileWriter.writeToFile(path, reportResult.getReportContent(), reportResult.getFileName());

        String resultContent = readContent(path + "/" + reportResult.getFileName());

        assertEquals(resultContent, content);

        File file = new File(path + "/" + reportResult.getFileName());
        boolean delete = file.delete();
    }

    private String readContent(String path) throws IOException {
        File file = new File(path);
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] text = new byte[(int) file.length()];
            int read = inputStream.read(text);
            return new String(text);
        }
    }
}