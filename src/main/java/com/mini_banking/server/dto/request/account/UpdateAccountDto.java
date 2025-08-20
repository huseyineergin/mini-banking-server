package com.mini_banking.server.dto.request.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateAccountDto {
  private String name;
}
