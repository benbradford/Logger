package com.bradford.log.handler.writer;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PrettyTimestampCreator {

  private static final SimpleDateFormat PRINT_TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss");

  public String makePretty(long timestamp) {
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(timestamp);
    return PRINT_TIME_FORMATTER.format(c.getTime());
  }

}
