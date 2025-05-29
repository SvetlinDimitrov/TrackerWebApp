package org.record.infrastructure.config.security.evaluator;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.record.features.meal.services.MealService;
import org.record.infrastructure.config.security.SecurityUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MealEvaluator {

  private final MealService mealService;

  public boolean isOwner(UUID mealId) {
    var user = SecurityUtils.getCurrentLoggedInUser();

    return mealService.isOwner(mealId, user.id());
  }
}
