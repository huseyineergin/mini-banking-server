package com.mini_banking.server.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mini_banking.server.dto.request.LoginRequest;
import com.mini_banking.server.entity.User;
import com.mini_banking.server.repository.UserRepository;
import com.mini_banking.server.service.JwtService;
import com.mini_banking.server.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private JwtService jwtService;

  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  void testLogin_Success() {
    LoginRequest loginRequest = LoginRequest.builder()
        .username("test_username")
        .password("test_password")
        .build();

    User user = User.builder()
        .username("test_username")
        .password("encodedPassword")
        .build();

    when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
    when(jwtService.generateToken(user)).thenReturn("test_token");

    String token = userService.login(loginRequest);

    assertEquals("test_token", token);
  }
}
