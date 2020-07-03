package com.bradford.log.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class BatchRequest {

  private List<Request> requests;

  public BatchRequest() {
    requests = new ArrayList<>();
  }

  public BatchRequest(List<Request> requests) {
    this.requests = requests;
  }

  public List<Request> getRequests() {
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
