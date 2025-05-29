package org.auth.infrastructure.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.exceptions.ExceptionMessage;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessages implements ExceptionMessage {
  USER_NOT_FOUND("User not found with id: %s", "User not found"),
  USER_NOT_FOUND_EMAIL("User not found with email: %s", "User not found"),
  INVALID_USERNAME_OR_PASSWORD("Invalid username or password", "Invalid Credentials"),
  FORBIDDEN("Forbidden", "Forbidden"),
  ;

  private final String message;
  private final String title;
}
