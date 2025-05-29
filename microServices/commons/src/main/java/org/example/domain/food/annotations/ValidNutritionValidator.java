package org.example.domain.food.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.domain.food.enums.AllowedNutrients;
import org.example.domain.food.shared.NutritionRequest;

public class ValidNutritionValidator
    implements ConstraintValidator<ValidNutrition, NutritionRequest> {

  @Override
  public boolean isValid(NutritionRequest nutrition, ConstraintValidatorContext context) {
    return nutrition != null
        && AllowedNutrients.isValidNutrition(nutrition.name(), nutrition.unit());
  }
}
