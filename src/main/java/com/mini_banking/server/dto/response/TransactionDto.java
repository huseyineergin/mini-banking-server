package com.mini_banking.server.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDto {
  private Long id;
  private String fromAccountNumber;
  private String toAccountNumber;
  private BigDecimal amount;
  private LocalDateTime transactionDate;
  private String status;
}