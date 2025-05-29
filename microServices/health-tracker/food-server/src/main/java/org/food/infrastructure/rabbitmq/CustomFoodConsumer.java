package org.food.infrastructure.rabbitmq;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rabbitmq.RabbitMqUserQueues;
import org.food.features.custom.service.CustomFoodService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomFoodConsumer {

  private final CustomFoodService customFoodService;

  @RabbitListener(queues = RabbitMqUserQueues.CUSTOM_FOOD_USER_DELETION)
  public void receiveMessage(String message) {
    UUID uuid = UUID.fromString(message);

    customFoodService.deleteAllByUserId(uuid);
    log.info("Deleted all custom foods for user with id: {}", uuid);
  }
}
