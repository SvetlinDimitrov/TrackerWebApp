package org.record.infrastructure.rabbitmq;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rabbitmq.RabbitMqUserQueues;
import org.example.util.UserExtractor;
import org.record.features.meal.dto.MealRequest;
import org.record.features.meal.services.MealService;
import org.record.features.record.dto.RecordCreateRequest;
import org.record.features.record.services.RecordService;
import org.record.features.record.utils.RecordUtils;
import org.record.infrastructure.mappers.RecordMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecordConsumer {

  private final RecordService recordService;
  private final MealService mealService;
  private final RecordMapper recordMapper;

  @RabbitListener(queues = RabbitMqUserQueues.RECORD_USER_CREATE)
  public void recordFirstCreation(String userToken) {
    var user = UserExtractor.get(userToken);
    var record = recordMapper.toEntity(new RecordCreateRequest(null), user);

    double BMR = RecordUtils.getBmr(record.getUserDetails());
    double caloriesPerDay = RecordUtils.getCaloriesPerDay(record.getUserDetails(), BMR);

    record.setDailyCalories(caloriesPerDay);
    record.setUserId(user.id());

    var savedRecord = recordService.save(record);

    List<MealRequest> mealRequests =
        List.of(
            new MealRequest("Breakfast"),
            new MealRequest("Lunch"),
            new MealRequest("Dinner"),
            new MealRequest("Snack"));

    mealService.createMultiple(savedRecord, mealRequests);

    log.info("Record created for user with id: {}", user.id());
    log.info("Meals created for record with id: {}", savedRecord.getId());
  }

  @RabbitListener(queues = RabbitMqUserQueues.RECORD_USER_DELETION)
  public void deleteUser(String message) {
    var userId = UUID.fromString(message);

    recordService.deleteAllByUserId(userId);

    log.info("Records deleted for user with id: {}", userId);
  }
}
