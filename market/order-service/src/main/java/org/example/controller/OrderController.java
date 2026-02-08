package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateOrderRequest;
import org.example.dto.OrderResponse;
import org.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
 * REST-контроллер для работы с заказами.
 * создание заказов
 * получение списка заказов пользователя
 * получение информации о конкретном заказе
 * Идентификация пользователя осуществляется через HTTP-заголовок {X-User-Id}.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * Health-check endpoint.
     * Используется для проверки доступности сервиса.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Order Service is healthy! Time: " + new Date());
    }

    /**
     * Создание нового заказа.
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody CreateOrderRequest request,
            @RequestHeader("X-User-Id") Long userId) {
        OrderResponse response = orderService.createOrder(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Получение списка заказов текущего пользователя.
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getUserOrders(
            @RequestHeader("X-User-Id") Long userId) {
        List<OrderResponse> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    /**
     * Получение информации о конкретном заказе.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable UUID orderId,
            @RequestHeader("X-User-Id") Long userId) {
        OrderResponse order = orderService.getOrder(orderId, userId);
        return ResponseEntity.ok(order);
    }
}
