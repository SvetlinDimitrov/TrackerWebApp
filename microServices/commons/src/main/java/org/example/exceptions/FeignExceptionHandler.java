package org.example.exceptions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FeignExceptionHandler {

  @ExceptionHandler(FeignException.class)
  public ResponseEntity<ProblemDetail> handleFeignStatusException(FeignException e) {
    HttpStatus status = HttpStatus.resolve(e.status());
    String rawContent = e.contentUTF8();

    String message = "An error occurred while processing your request.";

    if (rawContent != null) {
      try {
        JsonObject json = JsonParser.parseString(rawContent).getAsJsonObject();
        if (json.has("message")) {
          message = json.get("message").getAsString();
        }
      } catch (Exception parseException) {
        message = "Failed to parse error response.";
      }
    }

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR, message);
    problemDetail.setTitle("Feign Client Error");

    return new ResponseEntity<>(
        problemDetail, status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
