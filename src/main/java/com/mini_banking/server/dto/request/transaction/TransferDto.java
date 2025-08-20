package com.mini_banking.server.dto.request.transaction;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferDto {
  private String fromAccountNumber;
  private String toAccountNumber;
  private BigDecimal amount;
}
