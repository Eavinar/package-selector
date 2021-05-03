package com.mobiquity.exception;

/**
 * Exception class for packaging API.
 * API related exceptions should be delegated to this class.
 */
public class APIException extends Exception {

  public APIException(String message, Exception e) {
    super(message, e);
  }

  public APIException(String message) {
    super(message);
  }
}
