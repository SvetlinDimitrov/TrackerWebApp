package org.record.infrastructure.config.security.evaluator;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.record.features.food.service.FoodService;
import org.record.infrastructure.config.security.SecurityUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodEvaluator {

  private final FoodService foodService;

  public boolean isOwner(UUID mealId, UUID foodId) {
    var user = SecurityUtils.getCurrentLoggedInUser();

    return foodService.isOwner(mealId, foodId, user.id());
  }
}
