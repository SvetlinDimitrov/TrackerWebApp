package org.record.features.meal.dto;

import org.hibernate.validator.constraints.Length;

public record MealRequest(
    @Length(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
        String name) {}
