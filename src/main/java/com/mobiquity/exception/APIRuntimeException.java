package com.mobiquity.exception;

/**
 * Exception class for packaging API.
 * API related exceptions should be delegated to this class.
 */
public class APIRuntimeException extends RuntimeException {

  public APIRuntimeException(String message, Exception e) {
    super(message, e);
  }

  public APIRuntimeException(String message) {
    super(message);
  }
}
