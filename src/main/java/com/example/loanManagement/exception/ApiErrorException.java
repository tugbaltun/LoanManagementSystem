package com.example.loanManagement.exception;

public class ApiErrorException extends RuntimeException {
  private static final long serialVersionUID = 1159929890551754488L;
  private final String message;

  public ApiErrorException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
