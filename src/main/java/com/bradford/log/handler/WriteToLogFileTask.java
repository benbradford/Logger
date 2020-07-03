package com.bradford.log.handler;

import com.bradford.log.controller.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class WriteToLogFileTask implements Task {

  private static final String LOG_LOCATION = "/tmp/logs";
  private static final String FILE_NAME = "logfile.txt";
  private static final DateTimeFormatter FILE_PATH_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
  private static final SimpleDateFormat PRINT_TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss");

  private static final Logger LOG = LoggerFactory.getLogger(WriteToLogFileTask.class);

  private List<Request> requests;

  public WriteToLogFileTask(Request request) {
    requests = Arrays.asList(request);
  }

  public WriteToLogFileTask(List<Request> requests) {
    this.requests = requests;
  }

  @Override
  public void execute() {
    createWriter().ifPresent(this::writeLogs);
  }

  @Override
  public void close() {

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

  private Optional<Writer> createWriter() {
    try {
      String filePath = getFilePath();
      String fileName = String.format("%s/%s", filePath, FILE_NAME);
      LOG.info("opening log file " + filePath);
      File logFilePath = new File(filePath);

      File logFile = new File(fileName);
      LOG.info("created file");
      logFilePath.mkdirs();
      LOG.info("created dirs");
      logFile.createNewFile();
      LOG.info("created new file");
      return Optional.of(new BufferedWriter(new FileWriter(fileName, true)));
    } catch (Exception e) {
      LOG.error("cannot create log file " + e);
      return Optional.empty();
    }
  }

  private void writeLogs(Writer writer) {
    LOG.info("writing log");
    try {
      requests.forEach(request -> writeToFile(request, writer));
    } finally {
      try {
        writer.close();
      } catch (IOException e) {
        LOG.error("Cannot close file " + e);
      }
    }
  }

  private void writeToFile(Request request, Writer writer) {
    String prettyLogTime = convertTimestampToReadableString(request.getTimestamp());
    String prettyWriteTime = convertTimestampToReadableString(System.currentTimeMillis());
    String logLevel = getPrettyLogLevel(request.getLogLevel());
    String log = String.format("%s (%s) [%s]: %s", prettyLogTime, prettyWriteTime, logLevel, request.getMessage());
    try {
      writer.append(log);
      writer.write(System.getProperty( "line.separator" ));
    } catch (IOException e) {
      LOG.error("Cannot writeToFile " + e);
    }
  }

  private String convertTimestampToReadableString(long timestamp) {
    if (timestamp < 15937587890L) {
      // time must be in seconds, convert to millis
      timestamp = timestamp * 1000L;
    }
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(timestamp);
    return PRINT_TIME_FORMATTER.format(c.getTime());
  }

  private String getPrettyLogLevel(int logLevel) {
    switch(logLevel) {
      case 1: return "Debug";
      case 2: return "Info";
      case 3: return "Warn";
      case 4: return "Error";
      case 5: return "Fatal";
    }
    return String.format("LogLevel(%d)", logLevel);
  }
}
