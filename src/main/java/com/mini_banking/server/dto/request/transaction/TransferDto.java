package com.mini_banking.server.dto.request.transaction;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferDto {
  @NotEmpty(message = "Sender account is required.")
  private String fromAccountNumber;
  @NotEmpty(message = "Receiver account is required.")
  private String toAccountNumber;
  @NotNull(message = "Amount is required.")
  @Positive(message = "Amount must be greater than zero.")
  private BigDecimal amount;
}
