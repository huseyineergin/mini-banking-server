package com.mini_banking.server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini_banking.server.dto.ApiResponse;
import com.mini_banking.server.dto.request.transaction.TransferDto;
import com.mini_banking.server.entity.Transaction;
import com.mini_banking.server.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
@Tag(name = "Transactions", description = "Transaction management endpoints.")
public class TransactionController {
  private final TransactionService transactionService;

  @PostMapping("/transfer")
  @Operation(summary = "Transfer money", description = "Transfers money from one account to another.")
  public ResponseEntity<ApiResponse<Transaction>> transfer(@RequestBody TransferDto dto) {
    Transaction transaction = transactionService.transferMoney(dto);
    ApiResponse<Transaction> response = ApiResponse.success(
        "Transaction completed.", 200, transaction //
    );
    return ResponseEntity.ok(response);
  }

  @GetMapping("/account/{accountId}")
  @Operation(summary = "Get transaction history of an account", description = "Retrieves the transaction history for an account.")
  public ResponseEntity<ApiResponse<List<Transaction>>> getTransactionHistory(@PathVariable String accountId) {
    List<Transaction> transactions = transactionService.getTransactionHistory(accountId);
    ApiResponse<List<Transaction>> response = ApiResponse.success(
        "Transactions found.", 200, transactions //
    );
    return ResponseEntity.ok(response);
  }
}
