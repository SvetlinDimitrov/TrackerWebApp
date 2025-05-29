package org.example.domain.food.enums;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AllowedCalorieUnits {
  CALORIE("kcal");

  private final String symbol;

  public static boolean isValidUnit(String unit) {
    return Arrays.stream(values()).anyMatch(allowedUnit -> allowedUnit.getSymbol().equals(unit));
  }
}
