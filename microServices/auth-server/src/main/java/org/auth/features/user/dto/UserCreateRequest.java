package org.auth.features.user.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.auth.infrastructure.annotations.NotUsedEmailConstraint;
import org.auth.infrastructure.annotations.ValidPassword;
import org.example.domain.user.enums.Gender;
import org.example.domain.user.enums.WorkoutState;

public record UserCreateRequest(
    @Size(min = 4, message = "Username must be at least 4 characters long")
        @NotBlank(message = "Username cannot be blank")
        String username,
    @NotUsedEmailConstraint(message = "Email is already in use")
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,
    @NotBlank(message = "Password cannot be blank")
        @ValidPassword(message = "Password must meet the complexity requirements")
        String password,
    @DecimalMin(value = "5.0", message = "Weight must be at least 5 kilograms")
        @NotNull(message = "Weight cannot be null")
        Double kilograms,
    @NotNull(message = "Workout state is required") WorkoutState workoutState,
    @NotNull(message = "Gender is required") Gender gender,
    @DecimalMin(value = "50.0", message = "Height must be at least 50 cm")
        @NotNull(message = "Height cannot be null")
        Double height,
    @Min(value = 1, message = "Age must be at least 1") @NotNull(message = "Age cannot be null")
        Integer age) {}
