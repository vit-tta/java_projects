package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.event.OrderCreatedEvent;
import org.example.dto.CreateOrderRequest;
import org.example.dto.OrderResponse;
import org.example.model.order.Order;
import org.example.model.order.OrderStatus;
import org.example.model.event.OutboxEvent;
import org.example.model.event.OutboxEventStatus;
import org.example.repository.OrderRepository;
import org.example.repository.OutboxEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
/**
 * Сервис заказов.
 * Отвечает за:
 *  - создание заказов
 *  - получение списка заказов пользователя
 *  - получение информации о заказе
 *  - обновление статуса заказа
 * Использует паттерн Transactional Outbox для гарантированной
 * доставки событий о создании заказа в Kafka.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request, Long userId) {
        // Создаем заказ
        Order order = new Order();
        order.setUserId(userId);
        order.setAmount(request.getAmount());
        order.setDescription(request.getDescription());
        order.setStatus(OrderStatus.PENDING);
        order = orderRepository.save(order);

        log.info("Order created with id: {}", order.getId());

        // Создаем событие для outbox
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(order.getId())
                .userId(userId)
                .amount(order.getAmount())
                .build();

        try {
            String payload = objectMapper.writeValueAsString(event);

            OutboxEvent outboxEvent = new OutboxEvent();
            outboxEvent.setAggregateType("ORDER");
            outboxEvent.setAggregateId(order.getId().toString());
            outboxEvent.setEventType("ORDER_CREATED");
            outboxEvent.setPayload(payload);
            outboxEvent.setStatus(OutboxEventStatus.PENDING);

            outboxEventRepository.save(outboxEvent);
            outboxEventRepository.flush();
            log.info("Outbox event saved for order: {}", order.getId());
        } catch (Exception e) {
            log.error("Failed to save outbox event for order: {}", order.getId(), e);
            throw new RuntimeException("Failed to create order");
        }

        return OrderResponse.from(order);
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponse::from)
                .toList();
    }

    public OrderResponse getOrder(UUID orderId, Long userId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderResponse.from(order);
    }

    @Transactional
    public void updateOrderStatus(UUID orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
        log.info("Order {} status updated to {}", orderId, status);
    }
}