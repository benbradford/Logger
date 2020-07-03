package com.bradford.log.handler;

public interface Task extends AutoCloseable {
  void execute();
}
