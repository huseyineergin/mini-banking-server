package com.mini_banking.server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini_banking.server.dto.ApiResponse;
import com.mini_banking.server.dto.request.AccountFilterDto;
import com.mini_banking.server.dto.request.CreateAccountDto;
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
  public ResponseEntity<ApiResponse<List<Account>>> searchAccounts(@Valid @RequestBody AccountFilterDto dto) {
    List<Account> accounts = accountService.getAccounts(dto);
    ApiResponse<List<Account>> response = ApiResponse.success(
        "Accounts found.", 200, accounts //
    );
    return ResponseEntity.status(200).body(response);

  }
}
