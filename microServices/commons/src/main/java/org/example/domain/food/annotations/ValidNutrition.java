package org.example.domain.food.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidNutritionValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNutrition {
  String message() default "Invalid nutrition name or unit.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
