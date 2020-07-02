package com.bradford.log.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class Controller {

  private static final Logger LOG = LoggerFactory.getLogger(Controller.class);


  @RequestMapping(value = "/v1/isAlive/test", method = RequestMethod.GET)
  public ResponseEntity<Response> testCall() {
    LOG.info("testCall");
    return Response.success("OKIOKOK");
  }

}
