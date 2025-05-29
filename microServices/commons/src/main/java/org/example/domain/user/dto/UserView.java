package org.example.domain.user.dto;

import java.util.UUID;
import org.example.domain.user.enums.Gender;
import org.example.domain.user.enums.UserRole;
import org.example.domain.user.enums.WorkoutState;

public record UserView(
    UUID id,
    String username,
    String email,
    Double kilograms,
    Double height,
    WorkoutState workoutState,
    Gender gender,
    UserRole role,
    Integer age) {}
