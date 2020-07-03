package com.bradford.log.controller;

import com.bradford.log.handler.writer.ComponentsFactory;
import com.bradford.log.handler.writer.WriteToLogFileTask;
import com.bradford.log.handler.TaskHandler;
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

  private TaskHandler handler;
  private ComponentsFactory factory;

  @Autowired
  public void setLogTaskHandler(TaskHandler handler) {
    this.handler = handler;
  }

  @Autowired
  public void setComponentFactory(ComponentsFactory factory) { this.factory = factory; }

  @RequestMapping(value = "/v1/isAlive", method = RequestMethod.GET)
  public ResponseEntity<Response> isAlive() {
    return Response.success("OKIOKOK");
  }

  @RequestMapping(value = "/v1/log", method = RequestMethod.POST)
  public ResponseEntity<Response> log(@RequestBody Request request) {
    handler.submit(new WriteToLogFileTask(factory, request));
    return Response.success("ok");
  }

  @RequestMapping(value = "/v1/batch", method = RequestMethod.POST)
  public ResponseEntity<Response> batch(@RequestBody String requests) {
    Gson gson = new Gson();
    BatchRequest requestList = gson.fromJson(requests, BatchRequest.class);
    List<Request> list = requestList.getRequests();

    handler.submit(new WriteToLogFileTask(factory, list));
    return Response.success("ok");
  }
}
