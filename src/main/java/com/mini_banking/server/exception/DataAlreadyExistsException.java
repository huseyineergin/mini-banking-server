package com.mini_banking.server.exception;

public class DataAlreadyExistsException extends RuntimeException {
  public DataAlreadyExistsException(String message) {
    super(message);
  }
}