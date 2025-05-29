package org.record.features.meal.services;

import java.util.List;
import java.util.UUID;
import org.record.features.meal.dto.MealRequest;
import org.record.features.meal.dto.MealView;
import org.record.features.meal.entity.Meal;
import org.record.features.record.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MealService {

  Page<MealView> getAll(UUID recordId, Pageable pageable);

  MealView getById(UUID mealId);

  MealView create(UUID recordId, MealRequest dto);

  MealView update(UUID mealId, MealRequest dto);

  void delete(UUID mealId);

  Meal findByIdAndUserId(UUID storageId, UUID userId);

  void createMultiple(Record record, List<MealRequest> dtos);

  boolean isOwner(UUID mealId, UUID userId);
}
