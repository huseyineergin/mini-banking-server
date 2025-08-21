package com.mini_banking.server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini_banking.server.dto.ApiResponse;
import com.mini_banking.server.dto.request.account.AccountFilterDto;
import com.mini_banking.server.dto.request.account.CreateAccountDto;
import com.mini_banking.server.dto.request.account.UpdateAccountDto;
import com.mini_banking.server.entity.Account;
import com.mini_banking.server.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "Account management endpoints.")
public class AccountController {

  private final AccountService accountService;

  @PostMapping
  @Operation(summary = "Create a new account", description = "Creates a new account for the authenticated user.")
  public ResponseEntity<ApiResponse<Account>> createAccount(@Valid @RequestBody CreateAccountDto dto) {
    Account account = accountService.createAccount(dto);
    ApiResponse<Account> response = ApiResponse.success(
        "Account is created.", 201, account //
    );
    return ResponseEntity.status(201).body(response);
  }

  @PostMapping("/search")
  @Operation(summary = "Search accounts", description = "Search accounts for the authenticated user.")
  public ResponseEntity<ApiResponse<List<Account>>> searchAccounts(@Valid @RequestBody AccountFilterDto dto) {
    List<Account> accounts = accountService.getAccounts(dto);
    ApiResponse<List<Account>> response = ApiResponse.success(
        "Accounts found.", 200, accounts //
    );
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update an account", description = "Updates the selected account for the authenticated user.")
  public ResponseEntity<ApiResponse<Account>> updateAccount(
      @PathVariable("id") String accountId,
      @Valid @RequestBody UpdateAccountDto dto //
  ) {
    Account account = accountService.updateAccount(accountId, dto);
    ApiResponse<Account> response = ApiResponse.success(
        "Account updated.", 200, account //
    );
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete an account", description = "Deletes the selected account for the authenticated user.")
  public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable("id") String accountId) {
    accountService.deleteAccount(accountId);
    ApiResponse<Void> response = ApiResponse.success(
        "Account deleted.", 0, null //
    );
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get details of an account", description = "Retrieves details of an account for the authenticated user.")
  public ResponseEntity<ApiResponse<Account>> getAccountDetails(@PathVariable("id") String accountId) {
    Account account = accountService.getAccount(accountId);
    ApiResponse<Account> response = ApiResponse.success("Account found.", 0, account);
    return ResponseEntity.ok(response);
  }
}
