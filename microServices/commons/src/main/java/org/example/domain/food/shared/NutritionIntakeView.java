package org.example.domain.food.shared;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NutritionIntakeView {

  private String name;
  private Double dailyConsumed;
  private Double recommendedIntake;
  private String measurement;
}
