package com.mini_banking.server.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mini_banking.server.dto.request.authentication.LoginDto;
import com.mini_banking.server.dto.request.authentication.RegisterDto;
import com.mini_banking.server.entity.User;
import com.mini_banking.server.exception.DataAlreadyExistsException;
import com.mini_banking.server.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public String register(RegisterDto dto) {
    if (userRepository.existsByUsername(dto.getUsername())) {
      throw new DataAlreadyExistsException("Username is already in use.");
    }

    if (userRepository.existsByEmail(dto.getEmail())) {
      throw new DataAlreadyExistsException("Email is already in use.");
    }

    String encodedPassword = passwordEncoder.encode(dto.getPassword());

    User user = User.builder()
        .username(dto.getUsername())
        .password(encodedPassword)
        .email(dto.getEmail())
        .build();

    userRepository.save(user);

    return jwtService.generateToken(user);
  }

  public String login(LoginDto dto) {
    User user = userRepository.findByUsername(dto.getUsername())
        .orElseThrow(() -> new EntityNotFoundException("Invalid username or password."));

    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
      throw new EntityNotFoundException("Invalid username or password.");
    }

    return jwtService.generateToken(user);
  }

}
