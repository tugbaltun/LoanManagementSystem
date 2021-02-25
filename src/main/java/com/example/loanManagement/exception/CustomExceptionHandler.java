package com.example.loanManagement.exception;

import com.example.loanManagement.entities.concretes.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(ApiErrorException.class)
  public ResponseEntity<ApiError> customHandleNotFound(Exception ex, WebRequest request) {
    ApiError errors = new ApiError();
    errors.setMessage(ex.getMessage());
    errors.setStatus(HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}
