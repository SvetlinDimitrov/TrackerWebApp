package org.example.exceptions.throwable;

import org.example.exceptions.ExceptionMessage;

public class BadRequestException extends BaseException {

  public BadRequestException(ExceptionMessage exceptionMessage) {
    super(exceptionMessage);
  }

  public BadRequestException(ExceptionMessage exceptionMessage, Object... values) {
    super(exceptionMessage, values);
  }
}
