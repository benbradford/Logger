package com.bradford.log.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;


public class TaskHandler {

  private static final Logger LOG = LoggerFactory.getLogger(TaskHandler.class);

  private final ConcurrentLinkedQueue<Task> tasks = new ConcurrentLinkedQueue<>();
  private ExecutorService executorService = Executors.newSingleThreadExecutor();
  private AtomicBoolean hasRequestedQuit = new AtomicBoolean(false);

  public TaskHandler() {
    executorService.execute(this::update);
  }

  public void submit(Task task) {
    if (isNotAcceptingNewTasks()) {
      LOG.error("Task poller has been shut down, will not exec task");
      return;
    }
    tasks.add(task);
  }

  public void destroy() {
    if (isNotAcceptingNewTasks() ) {
      LOG.warn("Cannot destroy, already destroyed");
      return;
    }
    LOG.info("Closing...");
    hasRequestedQuit.set(true);

    try {
      executorService.shutdown();
      LOG.info("Complete!");
    } catch (Exception e) {
      LOG.warn("Cannot shut down executor service with: " + e);
      try {
        executorService.shutdownNow();
      } catch (Exception e2) {
        LOG.error("Could not shut down now with: " + e2);
      }
    }
  }

  private boolean isNotAcceptingNewTasks() {
    return executorService.isShutdown() || executorService.isTerminated();
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
