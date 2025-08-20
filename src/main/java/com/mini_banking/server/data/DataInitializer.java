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

        Account account = Account.builder()
            .number("1000000000")
            .name("Work")
            .balance(BigDecimal.valueOf(10000))
            .user(user)
            .build();

        user.setAccounts(List.of(account));

        userRepository.save(user);
      }

      if (!userRepository.existsByUsername("jane.doe") && !userRepository.existsByEmail("jane.doe@example.com")) {
        User user = User.builder()
            .username("jane.doe")
            .password(passwordEncoder.encode("12345aA+"))
            .email("jane.doe@example.com")
            .build();

        Account account = Account.builder()
            .number("2000000000")
            .name("Personal")
            .balance(BigDecimal.valueOf(20000))
            .user(user)
            .build();

        user.setAccounts(List.of(account));

        userRepository.save(user);
      }
    };
  }
}
