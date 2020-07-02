package com.bradford.log;

import com.bradford.log.handler.LogTaskHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan("com.bradford.log")
public class ApplicationConfiguration {

  @Bean(destroyMethod = "destroy")
  @Primary
  public LogTaskHandler requestLogTaskHandler() {
    return new LogTaskHandler();
  }

}
