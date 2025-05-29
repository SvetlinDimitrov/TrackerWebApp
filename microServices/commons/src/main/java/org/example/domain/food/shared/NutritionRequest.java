package org.example.domain.food.shared;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.domain.food.annotations.ValidNutrition;

@ValidNutrition
public record NutritionRequest(
    @NotBlank(message = "Name is required") String name,
    @NotBlank(message = "Unit is required") String unit,
    @NotNull(message = "Amount is required") @PositiveOrZero(message = "Amount cannot be negative")
        Double amount) {}
