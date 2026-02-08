package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.AccountRequest;
import org.example.dto.PaymentResponse;
import org.example.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
/**
 * REST-контроллер платежного сервиса.
 * Отвечает за:
 *  - создание счета пользователя
 *  - пополнение баланса
 *  - получение текущего баланса
 * Все операции выполняются в контексте пользователя,
 * идентификатор которого передается через HTTP-заголовок X-User-Id.
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestHeader("X-User-Id") Long userId) {
        paymentService.createAccount(userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/top-up")
    public ResponseEntity<PaymentResponse> topUpAccount(
            @RequestBody AccountRequest request,
            @RequestHeader("X-User-Id") Long userId) {
        paymentService.topUpAccount(userId, request.getAmount());
        return ResponseEntity.ok(new PaymentResponse("Account topped up successfully"));
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(
            @RequestHeader("X-User-Id") Long userId) {
        BigDecimal balance = paymentService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }
}