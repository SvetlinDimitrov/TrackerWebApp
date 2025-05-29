package org.example.domain.food.shared;

import jakarta.validation.Valid;
import java.util.List;
import org.hibernate.validator.constraints.Length;

public record FoodUpdateRequest(
    @Length(max = 500, message = "Name must be between 1 and 255 characters") String name,
    @Valid CalorieRequest calories,
    @Valid ServingRequest mainServing,
    @Valid FoodInfoRequest foodDetails,
    @Valid List<ServingRequest> otherServing,
    @Valid List<NutritionRequest> nutrients) {}
