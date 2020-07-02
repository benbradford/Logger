package com.bradford.log.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class BatchLogRequest {

  private List<LogRequest> requests;

  public BatchLogRequest() {
    requests = new ArrayList<>();
  }

  public BatchLogRequest(List<LogRequest> requests) {
    this.requests = requests;
  }

  public List<LogRequest> getRequests() {
    return requests;
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
