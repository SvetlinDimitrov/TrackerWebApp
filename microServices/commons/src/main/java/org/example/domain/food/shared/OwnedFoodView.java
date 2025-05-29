package org.example.domain.food.shared;

import java.util.List;
import java.util.UUID;

public record OwnedFoodView(
    UUID id,
    String name,
    CalorieView calories,
    ServingView mainServing,
    FoodInfoView foodDetails,
    List<ServingView> otherServing,
    List<NutritionView> nutrients) {}
