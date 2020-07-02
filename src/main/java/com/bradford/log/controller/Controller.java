package com.bradford.log.controller;

import com.bradford.log.handler.LogToFileTask;
import com.bradford.log.handler.LogTaskHandler;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class Controller {

  private LogTaskHandler handler;

  @Autowired
  public void setLogTaskHandler(LogTaskHandler handler) {
    this.handler = handler;
  }

  @RequestMapping(value = "/v1/isAlive", method = RequestMethod.GET)
  public ResponseEntity<Response> isAlive() {
    return Response.success("OKIOKOK");
  }

  @RequestMapping(value = "/v1/stop", method = RequestMethod.GET)
  public ResponseEntity<Response> stop() {
    handler.destroy();
    return Response.success("done");
  }

  @RequestMapping(value = "/v1/log", method = RequestMethod.POST)
  public ResponseEntity<Response> log(@RequestBody LogRequest request) {
    handler.submit(new LogToFileTask(request));
    return Response.success("ok");
  }

  @RequestMapping(value = "/v1/batch", method = RequestMethod.POST)
  public ResponseEntity<Response> batch(@RequestBody String requests) {
    Gson gson = new Gson();
    BatchLogRequest requestList = gson.fromJson(requests, BatchLogRequest.class);
    List<LogRequest> list = requestList.getRequests();

    handler.submit(new LogToFileTask(list));
    return Response.success("ok");
  }
}
