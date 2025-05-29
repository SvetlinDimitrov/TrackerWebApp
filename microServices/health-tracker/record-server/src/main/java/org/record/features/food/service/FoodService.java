package org.record.features.food.service;

import java.util.UUID;
import org.example.domain.food.shared.FoodRequest;
import org.example.domain.food.shared.OwnedFoodView;
import org.record.features.food.dto.FoodFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodService {

  OwnedFoodView create(UUID mealId, FoodRequest dto);

  void delete(UUID mealId, UUID foodId);

  OwnedFoodView get(UUID foodId, UUID mealId);

  OwnedFoodView update(UUID mealId, UUID foodId, FoodRequest dto);

  Page<OwnedFoodView> getAll(UUID mealId, FoodFilter filter, Pageable pageable);

  boolean isOwner(UUID mealId, UUID foodId, UUID userId);
}
