package org.example.domain.user.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import org.example.domain.user.enums.Gender;
import org.example.domain.user.enums.WorkoutState;
import org.hibernate.validator.constraints.Length;

public record UserEditRequest(
    @Length(min = 4, message = "Username must be at least 4 characters long") String username,
    @DecimalMin(value = "5", message = "Weight must be at least 5 kg") BigDecimal kilograms,
    WorkoutState workoutState,
    Gender gender,
    BigDecimal height,
    @Min(value = 1, message = "Age must be at least 1") Integer age) {}
