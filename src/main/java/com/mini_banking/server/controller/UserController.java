package com.mini_banking.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mini_banking.server.dto.ApiResponse;
import com.mini_banking.server.dto.request.LoginRequest;
import com.mini_banking.server.dto.request.RegisterRequest;
import com.mini_banking.server.entity.User;
import com.mini_banking.server.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<User>> register(@Valid @RequestBody RegisterRequest request) {
    User user = userService.register(request);
    ApiResponse<User> response = ApiResponse.success("User registered.", 201, user);
    return ResponseEntity.status(201).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<User>> login(@Valid @RequestBody LoginRequest request) {
    User user = userService.login(request);
    ApiResponse<User> response = ApiResponse.success("User logged in.", 200, user);
    return ResponseEntity.ok(response);
  }

}
