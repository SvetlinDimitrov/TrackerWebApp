package org.food.infrastructure.config.openfeign;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class NutritionixFeignConfig {

  @Value("${api.id}")
  private String xApiId;

  @Value("${api.key}")
  private String xApiKey;

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      requestTemplate.header("x-app-id", xApiId);
      requestTemplate.header("x-app-key", xApiKey);
    };
  }
}
