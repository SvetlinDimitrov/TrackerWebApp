package org.example.exceptions.throwable;

import org.example.exceptions.ExceptionMessage;

public class ForbiddenException extends BaseException {

  public ForbiddenException(ExceptionMessage exceptionMessage) {
    super(exceptionMessage);
  }
}
