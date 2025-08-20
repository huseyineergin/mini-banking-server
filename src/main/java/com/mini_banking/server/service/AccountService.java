package com.mini_banking.server.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mini_banking.server.dto.request.AccountFilterDto;
import com.mini_banking.server.dto.request.CreateAccountDto;
import com.mini_banking.server.entity.Account;
import com.mini_banking.server.entity.User;
import com.mini_banking.server.exception.DataAlreadyExistsException;
import com.mini_banking.server.repository.AccountRepository;
import com.mini_banking.server.repository.UserRepository;
import com.mini_banking.server.repository.specification.AccountSpecification;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final UserRepository userRepository;
  private final AccountRepository accountRepository;

  public Account createAccount(CreateAccountDto dto) {
    if (accountRepository.findByName(dto.getName()).isPresent()) {
      String.format("Account with the name \"%s\" already exists.", dto.getName());
      throw new DataAlreadyExistsException(
          String.format("Account with the name \"%s\" already exists.", dto.getName()) //
      );
    }

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    User user = userRepository.findByUsername(auth.getName())
        .orElseThrow(() -> new EntityNotFoundException("User not found."));

    String number = generateUniqueAccountNumber();

    Account account = Account.builder()
        .number(number)
        .name(dto.getName())
        .balance(BigDecimal.ZERO)
        .user(user)
        .build();

    return accountRepository.save(account);
  }

  public List<Account> getAccounts(AccountFilterDto filterDto) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    Specification<Account> spec = AccountSpecification.belongsToUser(auth.getName())
        .and(AccountSpecification.hasNumber(filterDto.getNumber()))
        .and(AccountSpecification.hasName(filterDto.getName()));

    return accountRepository.findAll(spec);
  }

  private String generateUniqueAccountNumber() {
    String number;
    do {
      number = generateRandomNumber();
    } while (accountRepository.existsByNumber(number));
    return number;
  }

  private String generateRandomNumber() {
    long min = 1000000000L, max = 9999999999L;
    long randomNumber = ThreadLocalRandom
        .current().nextLong(min, max + 1);
    return String.valueOf(randomNumber);
  }

}
