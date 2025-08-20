package com.mini_banking.server.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAccountDto {
  private String name;
}
