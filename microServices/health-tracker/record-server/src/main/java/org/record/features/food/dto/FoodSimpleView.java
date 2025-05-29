package org.record.features.food.dto;

import org.example.domain.food.shared.CalorieView;
import org.example.domain.food.shared.ServingView;

public record FoodSimpleView(String name, CalorieView calories, ServingView serving) {}
