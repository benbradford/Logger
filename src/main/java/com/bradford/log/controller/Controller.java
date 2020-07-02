package com.bradford.log.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class Controller {

  private static final Logger LOG = LoggerFactory.getLogger(Controller.class);

  @RequestMapping(value = "/v1/isAlive", method = RequestMethod.GET)
  public ResponseEntity<Response> isAlive() {
    LOG.info("testCall");
    return Response.success("OKIOKOK");
  }

  @RequestMapping(value = "/v1/log", method = RequestMethod.POST)
  public ResponseEntity<Response> log(@RequestBody LogRequest request) {
    LOG.info("logging " + request);
    return Response.success("ok");
  }

  @RequestMapping(value = "/v1/batch", method = RequestMethod.POST)
  public ResponseEntity<Response> batch(@RequestBody String requests) {
    Gson gson = new Gson();
    BatchLogRequest requestList = gson.fromJson(requests, BatchLogRequest.class);

    List<LogRequest> list = requestList.getRequests();

    LOG.info("logging " + list);
    return Response.success("ok");
  }
}
