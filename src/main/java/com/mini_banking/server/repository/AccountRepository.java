package com.mini_banking.server.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mini_banking.server.entity.Account;

public interface AccountRepository extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account> {
  Optional<Account> findByNumber(String number);

  Optional<Account> findByName(String name);

  boolean existsByNumber(String number);
}
