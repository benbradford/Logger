package com.bradford.log.handler;

public interface Task {
  void execute();
  // :TODO: add a function to return an array of locks which this task requires - this could be the filename of the file to write to for instance
}
