package com.bradford.log.handler;

import com.bradford.log.controller.LogRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class LogToFileTask implements LogTask {

  private static final Logger LOG = LoggerFactory.getLogger(LogToFileTask.class);

  private List<LogRequest> requests;

  public LogToFileTask(LogRequest request) {
    requests = Arrays.asList(request);
  }

  public LogToFileTask(List<LogRequest> requests) {
    this.requests = requests;
  }

  @Override
  public void execute() {
    LOG.info("Executing task!");
  }

  @Override
  public void close() {

  }
}
