package org.food.features.custom.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidMineralNutrientsValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMineralNutrients {
    String message() default "Invalid mineral nutrient name.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}