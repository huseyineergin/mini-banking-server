package com.mini_banking.server.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
  private String username;
  private String password;
  private String email;
}
