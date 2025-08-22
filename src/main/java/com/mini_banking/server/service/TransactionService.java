package com.mini_banking.server.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mini_banking.server.dto.request.transaction.TransferDto;
import com.mini_banking.server.dto.response.TransactionDto;
import com.mini_banking.server.entity.Account;
import com.mini_banking.server.entity.Transaction;
import com.mini_banking.server.exception.BadRequestException;
import com.mini_banking.server.exception.InsufficientBalanceException;
import com.mini_banking.server.exception.UnauthorizedException;
import com.mini_banking.server.mapper.TransactionMapper;
import com.mini_banking.server.repository.AccountRepository;
import com.mini_banking.server.repository.TransactionRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;

  @Transactional(noRollbackFor = InsufficientBalanceException.class)
  public TransactionDto transferMoney(TransferDto dto) {
    Account fromAccount = accountRepository.findByNumberForUpdate(dto.getFromAccountNumber())
        .orElseThrow(() -> new EntityNotFoundException("Sender account not found."));

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (!fromAccount.getUser().getUsername().equals(auth.getName())) {
      throw new UnauthorizedException("Unauthorized.");
    }

    Account toAccount = accountRepository.findByNumberForUpdate(dto.getToAccountNumber())
        .orElseThrow(() -> new EntityNotFoundException("Receiver account not found"));

    if (fromAccount.getNumber() == toAccount.getNumber()) {
      throw new BadRequestException("Sender and receiver accounts can not be the same.");
    }

    Transaction transaction = Transaction.builder()
        .from(fromAccount)
        .to(toAccount)
        .amount(dto.getAmount())
        .status(Transaction.TransactionStatus.FAILED)
        .build();

    if (fromAccount.getBalance().compareTo(dto.getAmount()) < 0) {
      transactionRepository.save(transaction);
      throw new InsufficientBalanceException("Insufficient balance.");
    }

    fromAccount.setBalance(fromAccount.getBalance().subtract(dto.getAmount()));
    toAccount.setBalance(toAccount.getBalance().add(dto.getAmount()));

    accountRepository.save(fromAccount);
    accountRepository.save(toAccount);

    transaction.setStatus(Transaction.TransactionStatus.SUCCESS);
    Transaction savedTx = transactionRepository.save(transaction);

    return TransactionMapper.toDto(savedTx);
  }

  public List<TransactionDto> getTransactionHistory(String accountId) {
    Account account = accountRepository.findById(UUID.fromString(accountId))
        .orElseThrow(() -> new EntityNotFoundException("Account not found."));

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (!account.getUser().getUsername().equals(auth.getName())) {
      throw new UnauthorizedException("Unauthorized.");
    }

    return transactionRepository.findByAccountId(UUID.fromString(accountId))
        .stream()
        .map(TransactionMapper::toDto)
        .toList();
  }

}
