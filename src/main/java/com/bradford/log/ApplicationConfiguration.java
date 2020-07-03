package com.bradford.log;

import com.bradford.log.handler.writer.ComponentsFactory;
import com.bradford.log.handler.writer.LogFileCreator;
import com.bradford.log.handler.writer.PrettyTimestampCreator;
import com.bradford.log.handler.TaskHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan("com.bradford.log")
public class ApplicationConfiguration {

  @Bean(destroyMethod = "destroy")
  @Primary
  public TaskHandler requestLogTaskHandler() {
    return new TaskHandler();
  }

  @Bean
  @Primary
  public LogFileCreator requestLogFileCreator() { return new LogFileCreator(); }

  @Bean
  @Primary
  public PrettyTimestampCreator requestPrettyTimestampCreator() { return new PrettyTimestampCreator(); }

  @Bean
  @Primary
  public ComponentsFactory requestComponentFactory() { return new ComponentsFactory(); }
}
