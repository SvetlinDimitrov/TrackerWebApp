package org.example.exceptions.throwable;

import org.example.exceptions.ExceptionMessage;

public class NotFoundException extends BaseException {

  public NotFoundException(ExceptionMessage exceptionMessage, Object... values) {
    super(exceptionMessage, values);
  }
}
