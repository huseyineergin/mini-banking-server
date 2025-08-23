package com.mini_banking.server.dto.request.account;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateAccountDto {
  @NotEmpty(message = "Name is required.")
  private String name;
}
