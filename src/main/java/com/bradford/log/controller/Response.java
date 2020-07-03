package com.bradford.log.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {

  public static class Error {
    private final String type;
    private final String message;

    private Error(String type, String message) {
      this.type = type;
      this.message = message;
    }

    public String getMessage() {
      return message;
    }

    public String getType() {
      return type;
    }
  }

  public static class Result {
    private String message;

    private Result(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }
  }

  private final Result result;
  private final Error error;

  private Response(Result result, Error error) {
    this.error = error;
    this.result = result;
  }

  public Error getError() {
    return error;
  }

  public Result getResult() {
    return result;
  }

  public static ResponseEntity<Response> success(String message) {
    Result result = new Result(message);
    Response response = new Response(result, null);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  public static ResponseEntity<Response>  badRequest(String errorMessage) {
    Error error = new Error("Bad Request", errorMessage);
    Response response = new Response(null, error);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  public static ResponseEntity<Response>  serverError(String errorMessage) {
    Error error = new Error("Server Error", errorMessage);
    Response response = new Response(null, error);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
