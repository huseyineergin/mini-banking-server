package com.mini_banking.server.mapper;

import com.mini_banking.server.dto.response.TransactionDto;
import com.mini_banking.server.entity.Transaction;

public class TransactionMapper {

  public static TransactionDto toDto(Transaction tx) {
    return TransactionDto.builder()
        .id(tx.getId())
        .fromAccountNumber(tx.getFrom() != null ? tx.getFrom().getNumber() : null)
        .toAccountNumber(tx.getTo() != null ? tx.getTo().getNumber() : null)
        .amount(tx.getAmount())
        .transactionDate(tx.getTransactionDate())
        .status(tx.getStatus().name())
        .build();
  }
}
