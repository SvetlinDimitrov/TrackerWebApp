package org.example.controllers;

import org.example.domain.dto.SingleErrorResponse;
import org.example.exceptions.AchievementException;
import org.example.exceptions.InvalidJsonTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AchievementControllerAdviser {
  @ExceptionHandler(AchievementException.class)
  public ResponseEntity<SingleErrorResponse> handleException(AchievementException e) {
    return new ResponseEntity<>(new SingleErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidJsonTokenException.class)
  public ResponseEntity<SingleErrorResponse> handleException(InvalidJsonTokenException e) {
    return new ResponseEntity<>(new SingleErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
  }
}
