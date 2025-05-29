package org.food.infrastructure.config;

import org.example.exceptions.CustomExceptionHandler;
import org.example.exceptions.FeignExceptionHandler;
import org.example.exceptions.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Application {

  @Bean
  public CustomExceptionHandler customExceptionHandler() {
    return new CustomExceptionHandler();
  }

  @Bean
  public GlobalExceptionHandler globalExceptionHandler() {
    return new GlobalExceptionHandler();
  }

  @Bean
  public FeignExceptionHandler feignExceptionHandler() {
    return new FeignExceptionHandler();
  }
}
