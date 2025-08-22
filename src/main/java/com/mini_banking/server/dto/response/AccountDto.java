package com.mini_banking.server.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {
  private String id;
  private String number;
  private String name;
  private BigDecimal balance;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}