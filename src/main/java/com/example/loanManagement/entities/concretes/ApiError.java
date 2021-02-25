package com.example.loanManagement.entities.concretes;

import lombok.Data;

@Data
public class ApiError {
  private int status;
  private String message;

}
