package org.example.domain.food.shared;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

public record ServingRequest(
    @NotNull(message = "Amount is required") @PositiveOrZero(message = "Amount cannot be negative")
        Double amount,
    @NotNull(message = "Serving weight is required")
        @PositiveOrZero(message = "Serving weight cannot be negative")
        Double servingWeight,
    @NotBlank(message = "Metric is required")
        @Length(max = 255, message = "Metric must be between 1 and 255 characters")
        String metric) {}
