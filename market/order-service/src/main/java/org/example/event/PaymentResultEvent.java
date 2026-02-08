package org.example.event;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;
/**
 * Событие-результат обработки платежа.
 * Публикуется Payment Service после попытки списания средств
 * и используется Order Service для обновления статуса заказа.
 * Является асинхронным ответом на событие {OrderCreatedEvent}.
 */
@Data
@Builder
public class PaymentResultEvent {
    private UUID orderId;
    private boolean successful;
}