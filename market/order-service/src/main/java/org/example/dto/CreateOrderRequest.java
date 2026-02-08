package org.example.dto;

import lombok.Data;
import java.math.BigDecimal;
/**
 * DTO для запроса на создание заказа.
 * Используется клиентом (через API Gateway) для передачи данных
 * в Order Service при создании нового заказа.
 */
@Data
public class CreateOrderRequest {
    private BigDecimal amount;
    private String description;
}
