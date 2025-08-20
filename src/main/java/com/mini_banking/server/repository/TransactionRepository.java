package com.mini_banking.server.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mini_banking.server.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  @Query("SELECT t FROM Transaction t WHERE t.from.id = :accountId OR t.to.id = :accountId ORDER BY t.transactionDate DESC")
  List<Transaction> findByAccountId(@Param("accountId") UUID accountId);
}
