package com.bradford.log.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogRequest {

  private String message;
  private String error;
  private int logLevel;

  public LogRequest() {

  }

  public LogRequest(int logLevel, String message, String error) {
    this.logLevel = logLevel;
    this.message = message;
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public String getError() {
    return error;
  }

  public int getLogLevel() {
    return logLevel;
  }

  @Override
  public String toString() {
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
