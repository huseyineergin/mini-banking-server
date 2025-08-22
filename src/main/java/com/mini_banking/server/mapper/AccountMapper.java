package com.mini_banking.server.mapper;

import com.mini_banking.server.dto.response.AccountDto;
import com.mini_banking.server.entity.Account;

public class AccountMapper {

  public static AccountDto toDto(Account account) {
    return AccountDto.builder()
        .id(account.getId().toString())
        .number(account.getNumber())
        .name(account.getName())
        .balance(account.getBalance())
        .createdAt(account.getCreatedAt())
        .updatedAt(account.getUpdatedAt())
        .build();
  }
}
