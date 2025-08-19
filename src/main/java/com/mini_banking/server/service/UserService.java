package com.mini_banking.server.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mini_banking.server.dto.request.LoginRequest;
import com.mini_banking.server.dto.request.RegisterRequest;
import com.mini_banking.server.entity.User;
import com.mini_banking.server.exception.UserAlreadyExistsException;
import com.mini_banking.server.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public String register(RegisterRequest request) {
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new UserAlreadyExistsException("Username is already taken.");
    }

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new UserAlreadyExistsException("Email is already in use.");
    }

    String encodedPassword = passwordEncoder.encode(request.getPassword());

    User user = User.builder()
        .username(request.getUsername())
        .password(encodedPassword)
        .email(request.getEmail())
        .build();

    userRepository.save(user);

    return jwtService.generateToken(user);
  }

  public String login(LoginRequest request) {
    User user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new EntityNotFoundException("Invalid username or password."));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new EntityNotFoundException("Invalid username or password.");
    }

    return jwtService.generateToken(user);
  }

}
