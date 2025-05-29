package org.food.features.custom.service;

import java.util.UUID;
import org.example.domain.food.shared.FoodRequest;
import org.example.domain.food.shared.OwnedFoodView;
import org.food.features.custom.dto.CustomFilterCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomFoodService {

  Page<OwnedFoodView> getAll(Pageable pageable, CustomFilterCriteria filterCriteria);

  OwnedFoodView getById(UUID id);

  OwnedFoodView create(FoodRequest dto);

  void delete(UUID id);

  OwnedFoodView update(UUID id, FoodRequest request);

  void deleteAllByUserId(UUID userId);

  boolean existsByUserIdAndFoodId(UUID id, UUID foodId);
}
