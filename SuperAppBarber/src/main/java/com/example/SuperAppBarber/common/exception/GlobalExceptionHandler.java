package com.example.SuperAppBarber.common.exception;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.SuperAppBarber.common.exception.enums.ErrorCode;
import com.example.SuperAppBarber.common.exception.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Business Exception
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        log.warn("BusinessException: {}", ex.getMessage());

        return ResponseEntity.badRequest().body(
                ApiResponse.<Void>builder()
                        .success(false)
                        .code(ex.getErrorCode().getCode())
                        .message(ex.getErrorCode().getMessage())
                        .build());
    }

    // Validation Exception (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(
                ApiResponse.<Map<String, String>>builder()
                        .success(false)
                        .code(ErrorCode.INVALID_REQUEST.getCode())
                        .message("Validation failed")
                        .data(errors)
                        .build());
    }

    // Access denied
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ApiResponse.<Void>builder()
                        .success(false)
                        .code(ErrorCode.FORBIDDEN.getCode())
                        .message(ErrorCode.FORBIDDEN.getMessage())
                        .build());
    }

    // All other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("Unexpected error", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.<Void>builder()
                        .success(false)
                        .code(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                        .message(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                        .build());
    }
}
