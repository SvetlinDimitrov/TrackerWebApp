package org.example.exceptions.throwable;

import lombok.Getter;
import org.example.exceptions.ExceptionMessage;

@Getter
public abstract class BaseException extends RuntimeException {

  private final ExceptionMessage exceptionMessage;

  protected BaseException(ExceptionMessage exceptionMessage, Object... values) {
    super(String.format(exceptionMessage.getMessage(), values));
    this.exceptionMessage = exceptionMessage;
  }

  protected BaseException(ExceptionMessage exceptionMessage) {
    super(exceptionMessage.getMessage());
    this.exceptionMessage = exceptionMessage;
  }

  public String getTitle() {
    return exceptionMessage.getTitle();
  }
}
