package org.food.infrastructure.config.security.evaluator;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.domain.user.dto.UserView;
import org.food.features.custom.service.CustomFoodService;
import org.food.infrastructure.config.security.SecurityUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomFoodEvaluator {

  private final CustomFoodService customFoodService;

  public boolean isOwner(UUID foodId) {
    UserView user = SecurityUtils.getCurrentLoggedInUser();

    return customFoodService.existsByUserIdAndFoodId(user.id(), foodId);
  }
}
