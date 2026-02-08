package org.example.event;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;
/**
 * Событие, публикуемое Order Service при создании нового заказа.
 * Используется для асинхронного взаимодействия между сервисами
 * (например, Order Service → Payment Service) через Kafka.
 */
@Data
@Builder
public class OrderCreatedEvent {
    private UUID orderId;
    private Long userId;
    private BigDecimal amount;
}
