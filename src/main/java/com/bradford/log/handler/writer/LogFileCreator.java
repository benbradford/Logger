package com.bradford.log.handler.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class LogFileCreator {

  private static final Logger LOG = LoggerFactory.getLogger(WriteToLogFileTask.class);
  private static final String LOG_LOCATION = "/tmp/logs";
  private static final DateTimeFormatter FILE_PATH_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

  public Optional<Writer> createWriter(String filename) {
    try {
      String filePath = getFilePath();
      String completeFileName = String.format("%s/%s", filePath, filename);
      createFileIfRequired(filePath, completeFileName);
      return Optional.of(new BufferedWriter(new FileWriter(completeFileName, true)));
    } catch (IOException e) {
      LOG.error("cannot create log file due to IO exception: " + e);
    } catch (Exception e) {
      LOG.error("cannot create log file due other exception: " + e);
    }
    return Optional.empty();
  }

  private String getFilePath() {
    String now = FILE_PATH_FORMATTER.format(LocalDateTime.now());
    String [] slashSplit = now.split("/");
    String year = slashSplit[0];
    String month = slashSplit[1];
    String day = slashSplit[2].substring(0, 2);
    String hour = slashSplit[2].substring(3).split(":")[0];

    return String.format("%s/%s/%s/%s/%s", LOG_LOCATION, year, month, day, hour);
  }

  private void createFileIfRequired(String path, String fileName) throws IOException {
    File logFile = new File(fileName);
    if (!logFile.exists()) {
      LOG.info("creating " + logFile);
      new File(path).mkdirs();
      logFile.createNewFile();
    }
  }
}
