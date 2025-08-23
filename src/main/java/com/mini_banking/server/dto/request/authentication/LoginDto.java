package com.mini_banking.server.dto.request.authentication;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {
  @NotEmpty(message = "Username is required.")
  private String username;
  @NotEmpty(message = "Password is required.")
  private String password;
}
