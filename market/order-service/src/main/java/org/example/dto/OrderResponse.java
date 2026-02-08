package org.example.dto;

import lombok.Builder;
import lombok.Data;
import org.example.model.order.Order;
import org.example.model.order.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
/**
 * DTO для ответа с информацией о заказе.
 * Используется для возврата данных о заказе клиенту
 * (через API Gateway) после создания заказа
 * или при запросе списка / конкретного заказа.
 */
@Data
@Builder
public class OrderResponse {
    private UUID id;
    private Long userId;
    private BigDecimal amount;
    private String description;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .amount(order.getAmount())
                .description(order.getDescription())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
