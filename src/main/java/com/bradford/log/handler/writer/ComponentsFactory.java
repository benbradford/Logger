package com.bradford.log.handler.writer;

import org.springframework.beans.factory.annotation.Autowired;

public class ComponentsFactory {

  private LogFileCreator logFileCreator;
  private PrettyTimestampCreator prettyTimestampCreator;

  @Autowired
  public void setPrettyTimestampCreator(PrettyTimestampCreator prettyTimestampCreator) {
    this.prettyTimestampCreator = prettyTimestampCreator;
  }

  @Autowired
  public void setLogFileCreator(LogFileCreator logFileCreator) { this.logFileCreator = logFileCreator; }

  public LogFileCreator getLogFileCreator() {
    return logFileCreator;
  }

  public PrettyTimestampCreator getPrettyTimestampCreator() {
    return prettyTimestampCreator;
  }
}
