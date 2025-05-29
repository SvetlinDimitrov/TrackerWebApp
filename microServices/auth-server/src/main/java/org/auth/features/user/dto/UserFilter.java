package org.auth.features.user.dto;

import org.example.domain.user.enums.Gender;
import org.example.domain.user.enums.UserRole;
import org.example.domain.user.enums.WorkoutState;

public record UserFilter(
    String username,
    String email,
    Double kilograms,
    Double height,
    Integer age,
    WorkoutState workoutState,
    Gender gender,
    UserRole role) {}
