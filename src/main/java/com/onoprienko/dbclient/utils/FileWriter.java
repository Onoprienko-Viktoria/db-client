package com.onoprienko.dbclient.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class FileWriter {
    public static void writeToFile(String path, String content, String fileName) throws IOException {
        File file = new File(path, fileName);
        boolean newFile = file.createNewFile();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            bufferedWriter.write(content);
            log.info("Write file on path: {}", file.getAbsolutePath());
        } catch (IOException e) {
            log.error("Exception while writing content in file: ", e);
        }
    }
}
