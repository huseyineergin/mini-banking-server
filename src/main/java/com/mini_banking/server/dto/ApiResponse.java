package com.mini_banking.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
  private boolean success;
  private String message;
  private int status;
  private T data;

  public static <T> ApiResponse<T> success(String message, int status, T data) {
    return new ApiResponse<>(true, message, status, data);
  }

  public static <T> ApiResponse<T> error(String message, int status) {
    return new ApiResponse<>(false, message, status, null);
  }
}