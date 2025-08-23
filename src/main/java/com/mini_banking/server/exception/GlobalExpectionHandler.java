package com.mini_banking.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mini_banking.server.dto.ApiResponse;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExpectionHandler {

  @ExceptionHandler(DataAlreadyExistsException.class)
  public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExistsException(DataAlreadyExistsException ex) {
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(ApiResponse.error(ex.getMessage(), HttpStatus.CONFLICT.value()));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiResponse<Void>> handleEntityNotFoundException(EntityNotFoundException ex) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ApiResponse<Void>> handleUnauthorizedException(UnauthorizedException ex) {
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ApiResponse.error(ex.getMessage(), HttpStatus.UNAUTHORIZED.value()));
  }

  @ExceptionHandler(InsufficientBalanceException.class)
  public ResponseEntity<ApiResponse<Void>> handleInsufficientBalanceException(InsufficientBalanceException ex) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiResponse<Void>> handleBadRequestException(BadRequestException ex) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    String errorMessage = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .findFirst()
        .map(error -> error.getDefaultMessage())
        .orElse("Validation failed.");
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error(errorMessage, HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
    String errorMessage = ex.getConstraintViolations()
        .stream()
        .findFirst()
        .map(cv -> cv.getMessage())
        .orElse("Validation failed.");
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error(errorMessage, HttpStatus.BAD_REQUEST.value()));
  }

}
