package com.mini_banking.server.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountFilterDto {
  private String number;
  private String name;
}
