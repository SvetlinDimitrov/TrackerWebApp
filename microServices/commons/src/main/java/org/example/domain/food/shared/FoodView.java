package org.example.domain.food.shared;

import java.util.List;

public record FoodView(
    String name,
    CalorieView calories,
    ServingView mainServing,
    FoodInfoView foodDetails,
    List<ServingView> otherServing,
    List<NutritionView> nutrients) {}
