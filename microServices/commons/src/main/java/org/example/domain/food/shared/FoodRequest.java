package org.example.domain.food.shared;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.hibernate.validator.constraints.Length;

public record FoodRequest(
    @NotBlank(message = "Name is required")
        @Length(max = 500, message = "Name must be between 1 and 255 characters")
        String name,
    @Valid @NotNull(message = "Calories are required") CalorieRequest calories,
    @Valid @NotNull(message = "main serving is required") ServingRequest mainServing,
    @Valid FoodInfoRequest foodDetails,
    @Valid List<ServingRequest> otherServing,
    @Valid List<NutritionRequest> nutrients) {}
