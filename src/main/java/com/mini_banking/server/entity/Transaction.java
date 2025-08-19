package com.mini_banking.server.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "from_account_id", nullable = false)
  private Account from;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "to_account_id", nullable = false)
  private Account to;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(nullable = false)
  private LocalDateTime transactionDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TransactionStatus status;

  public enum TransactionStatus {
    SUCCESS,
    FAILED
  }
}
