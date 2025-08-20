package com.mini_banking.server.dto.request.authentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDto {
  private String username;
  private String password;
  private String email;
}
