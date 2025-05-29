package org.record.features.record.utils;

import org.example.domain.food.enums.AllowedNutrients;

public class CalculateMacros {

  /*
  Calculate Macros:
  Protein: 25% of 2946.647 calories = (0.25 × 2946.647) / 4 calories per gram = 183.29 grams
  Carbohydrates: 50% of 2946.647 calories = (0.50 × 2946.647) / 4 calories per gram = 367.50 grams
  Fats: 25% of 2946.647 calories = (0.25 × 2946.647) / 9 calories per gram = 109.29 grams
  */
  public static double calculateMacros(
      String name, double dailyConsumedCalories, double distributedMacros) {
    double raw = distributedMacros * dailyConsumedCalories;

    return name.equals(AllowedNutrients.Protein.getNutrientName())
            || name.equals(AllowedNutrients.Carbohydrate.getNutrientName())
        ? raw / 4
        : raw / 9;
  }
}
