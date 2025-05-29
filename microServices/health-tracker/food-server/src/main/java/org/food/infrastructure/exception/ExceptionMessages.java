package org.food.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.exceptions.ExceptionMessage;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessages implements ExceptionMessage {
  INVALID_USER_TOKEN("Invalid user token", "Invalid user token"),
  INVALID_FOOD_TYPE("Invalid food type. Valid ones: branded , survey , final", "Invalid food type"),
  FOOD_NOT_FOUND("Food with id %s not found", "Food not found"),
  NUTRITION_NOT_FOUND_WITH_NAME("Nutrition with name %s not found", "Nutrition not found"),
  NUTRITIONIX_NOT_FOUND("Nutritionix API returned no results", "Nutritionix not found"),
  ;

  private final String message;
  private final String title;
}
