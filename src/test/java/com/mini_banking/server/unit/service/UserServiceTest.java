package com.mini_banking.server.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mini_banking.server.dto.request.authentication.LoginDto;
import com.mini_banking.server.entity.User;
import com.mini_banking.server.repository.UserRepository;
import com.mini_banking.server.service.JwtService;
import com.mini_banking.server.service.UserService;

import jakarta.persistence.EntityNotFoundException;

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
    LoginDto loginDto = LoginDto.builder()
        .username("test_username")
        .password("test_password")
        .build();

    User user = User.builder()
        .username("test_username")
        .password("encodedPassword")
        .build();

    when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);
    when(jwtService.generateToken(user)).thenReturn("test_token");

    String token = userService.login(loginDto);

    assertEquals("test_token", token);
  }

  @Test
  void testLogin_UserNotFound() {
    LoginDto loginDto = LoginDto.builder()
        .username("test_username")
        .password("test_password")
        .build();

    when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> userService.login(loginDto));
  }

  @Test
  void testLogin_WrongPassword() {
    LoginDto loginDto = LoginDto.builder()
        .username("test_username")
        .password("test_password")
        .build();

    User user = User.builder()
        .username("test_username")
        .password("encodedPassword")
        .build();

    when(userRepository.findByUsername("test_username")).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> userService.login(loginDto));
  }

}
