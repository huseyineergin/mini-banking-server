package com.mini_banking.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini_banking.server.dto.ApiResponse;
import com.mini_banking.server.dto.request.LoginDto;
import com.mini_banking.server.dto.request.RegisterDto;
import com.mini_banking.server.dto.response.AuthDto;
import com.mini_banking.server.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management endpoints.")
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  @Operation(summary = "Register a new user", description = "Registers a new user and returns a JWT token.")
  public ResponseEntity<ApiResponse<AuthDto>> register(@Valid @RequestBody RegisterDto request) {
    String token = userService.register(request);
    ApiResponse<AuthDto> response = ApiResponse.success(
        "User registered.",
        201,
        AuthDto.builder().token(token).build() //
    );
    return ResponseEntity.status(201).body(response);
  }

  @PostMapping("/login")
  @Operation(summary = "Authenticate a user", description = "Authenticates a user and returns a JWT token.")
  public ResponseEntity<ApiResponse<AuthDto>> login(@Valid @RequestBody LoginDto request) {
    String token = userService.login(request);
    ApiResponse<AuthDto> response = ApiResponse.success(
        "User logged in.",
        200,
        AuthDto.builder().token(token).build() //
    );
    return ResponseEntity.ok(response);
  }

}
