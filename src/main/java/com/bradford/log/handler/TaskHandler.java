package com.bradford.log.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;


public class TaskHandler {

  private static final Logger LOG = LoggerFactory.getLogger(TaskHandler.class);

  private ConcurrentLinkedDeque<Task> tasks = new ConcurrentLinkedDeque<>();
  private Thread taskPoller;
  private AtomicBoolean hasRequestedQuit = new AtomicBoolean(false);

  public TaskHandler() {
    taskPoller = new Thread(()->update());
    taskPoller.start();
  }

  public void submit(Task task) {
    if (taskPoller == null) {
      LOG.error("Task poller has been shut down, will not exec task");
      return;
    }
    tasks.add(task);
  }

  public void destroy() {
    if (taskPoller == null) {
      LOG.warn("Cannot destroy, already destroyed");
      return;
    }
    LOG.info("Closing...");
    hasRequestedQuit.set(true);

    try {
      taskPoller.join(3000);
    } catch (InterruptedException e) {
      LOG.warn("Cannot join with main thread: " + e);
    }
    LOG.info("Complete!");
    taskPoller = null;
  }

  private void update() {
    while (!hasRequestedQuit.get()) {
      Task task = tasks.poll();
      if (task != null) {
        task.execute();
      } else {
        Thread.yield();
      }
    }
  }
}