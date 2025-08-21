package com.mini_banking.server.data;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mini_banking.server.entity.Account;
import com.mini_banking.server.entity.User;
import com.mini_banking.server.repository.AccountRepository;
import com.mini_banking.server.repository.UserRepository;

@Configuration
public class DataInitializer {

  @Bean
  CommandLineRunner initDatabase(
      UserRepository userRepository,
      AccountRepository accountRepository,
      PasswordEncoder passwordEncoder ////
  ) {
    return args -> {
      if (!userRepository.existsByUsername("john.doe") && !userRepository.existsByEmail("john.doe@example.com")) {
        User user = User.builder()
            .username("john.doe")
            .password(passwordEncoder.encode("12345aA+"))
            .email("john.doe@example.com")
            .build();

        Account account1 = Account.builder()
            .number("1000000000")
            .name("TRY")
            .balance(BigDecimal.valueOf(10000))
            .user(user)
            .build();

        Account account2 = Account.builder()
            .number("1000000001")
            .name("USD")
            .balance(BigDecimal.valueOf(12500))
            .user(user)
            .build();

        Account account3 = Account.builder()
            .number("1000000002")
            .name("EUR")
            .balance(BigDecimal.valueOf(15000))
            .user(user)
            .build();

        user.setAccounts(List.of(account1, account2, account3));

        userRepository.save(user);
      }

      if (!userRepository.existsByUsername("jane.doe") && !userRepository.existsByEmail("jane.doe@example.com")) {
        User user = User.builder()
            .username("jane.doe")
            .password(passwordEncoder.encode("12345aA+"))
            .email("jane.doe@example.com")
            .build();

        Account account1 = Account.builder()
            .number("2000000000")
            .name("GBP")
            .balance(BigDecimal.valueOf(17500))
            .user(user)
            .build();

        Account account2 = Account.builder()
            .number("2000000001")
            .name("AUD")
            .balance(BigDecimal.valueOf(20000))
            .user(user)
            .build();

        Account account3 = Account.builder()
            .number("2000000002")
            .name("CAD")
            .balance(BigDecimal.valueOf(22500))
            .user(user)
            .build();

        user.setAccounts(List.of(account1, account2, account3));

        userRepository.save(user);
      }
    };
  }
}
