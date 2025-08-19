package com.mini_banking.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini_banking.server.dto.ApiResponse;
import com.mini_banking.server.dto.request.LoginRequest;
import com.mini_banking.server.dto.request.RegisterRequest;
import com.mini_banking.server.dto.response.AuthResponse;
import com.mini_banking.server.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
    String token = userService.register(request);
    ApiResponse<AuthResponse> response = ApiResponse.success(
        "User registered.",
        201,
        AuthResponse.builder().token(token).build() //
    );
    return ResponseEntity.status(201).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
    String token = userService.login(request);
    ApiResponse<AuthResponse> response = ApiResponse.success(
        "User logged in.",
        200,
        AuthResponse.builder().token(token).build() //
    );
    return ResponseEntity.ok(response);
  }

}
