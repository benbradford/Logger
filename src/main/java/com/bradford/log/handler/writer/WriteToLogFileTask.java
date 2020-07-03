package com.bradford.log.handler.writer;

import com.bradford.log.controller.Request;
import com.bradford.log.handler.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class WriteToLogFileTask implements Task {

  private static final Logger LOG = LoggerFactory.getLogger(WriteToLogFileTask.class);

  private final List<Request> requests;
  private final PrettyTimestampCreator prettyTimestampCreator;
  private final LogFileCreator logFileCreator;

  public WriteToLogFileTask(ComponentsFactory componentsFactory, Request request) {
    this(componentsFactory, Arrays.asList(request));
  }

  public WriteToLogFileTask(ComponentsFactory componentsFactory, List<Request> requests) {
    this.prettyTimestampCreator = componentsFactory.getPrettyTimestampCreator();
    this.logFileCreator = componentsFactory.getLogFileCreator();
    this.requests = requests;
  }

  @Override
  public void execute() {
    logFileCreator.createWriter().ifPresent(this::writeLogs);
  }

  private void writeLogs(Writer writer) {
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
    String prettyLogTime = prettyTimestampCreator.makePretty(request.getTimestamp());
    String prettyWriteTime = prettyTimestampCreator.makePretty(System.currentTimeMillis());
    String logLevel = getPrettyLogLevel(request.getLogLevel());
    String log = String.format("%s (%s) [%s]: %s", prettyLogTime, prettyWriteTime, logLevel, request.getMessage());
    try {
      writer.append(log);
      writer.write(System.getProperty( "line.separator" ));
    } catch (IOException e) {
      LOG.error("Cannot writeToFile " + e);
    }
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
