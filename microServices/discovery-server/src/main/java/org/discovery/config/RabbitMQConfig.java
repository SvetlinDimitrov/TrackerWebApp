package org.discovery.config;

import org.example.rabbitmq.RabbitMqUserQueues;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Bean
  public Queue queue() {
    return new Queue(RabbitMqUserQueues.RECORD_USER_CREATE, false);
  }

  @Bean
  public Queue queue2() {
    return new Queue(RabbitMqUserQueues.RECORD_USER_DELETION, false);
  }

  @Bean
  public Queue queue3() {
    return new Queue(RabbitMqUserQueues.CUSTOM_FOOD_USER_DELETION, false);
  }
}
