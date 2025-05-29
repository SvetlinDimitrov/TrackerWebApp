package org.example.rabbitmq;

import java.util.List;

public final class RabbitMqUserQueues {

  public static final String RECORD_USER_CREATE = "record_user_create_queue";
  public static final String RECORD_USER_DELETION = "record_user_deletion_queue";
  public static final String CUSTOM_FOOD_USER_DELETION = "custom_food_user_deletion_queue";

  private RabbitMqUserQueues() {}

  public static List<String> getAllQueuesUserCreate() {
    return List.of(RECORD_USER_CREATE);
  }

  public static List<String> getAllQueuesUserDeletion() {
    return List.of(RECORD_USER_DELETION, CUSTOM_FOOD_USER_DELETION);
  }
}
