package com.bradford.log.handler;

public interface LogTask extends AutoCloseable {
  void execute();
}
