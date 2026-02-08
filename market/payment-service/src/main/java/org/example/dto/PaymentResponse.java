package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Универсальный DTO-ответ для Payment Service.
 * Используется для возврата
 * простых текстовых сообщений клиенту.
 */
@Data
@AllArgsConstructor
public class PaymentResponse {
    private String message;
}
