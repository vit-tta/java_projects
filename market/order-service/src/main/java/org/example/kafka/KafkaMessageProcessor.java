package org.example.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.event.PaymentResultEvent;
import org.example.model.order.OrderStatus;
import org.example.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
/**
 * Kafka consumer, обрабатывающий результаты платежей.
 * Слушает топик {payment-result-events}, который публикуется
 * Payment Service после попытки списания средств.
 * Обработчик является идемпотентным
 * Повторное сообщение не ломает состояние заказа
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageProcessor {
    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "payment-result-events", groupId = "order-service-group")
    public void consumePaymentResult(String message) {
        try {
            PaymentResultEvent event = objectMapper.readValue(message, PaymentResultEvent.class);
            log.info("Received payment result for order {}: success = {}",
                    event.getOrderId(), event.isSuccessful());

            OrderStatus status = event.isSuccessful() ? OrderStatus.PAID : OrderStatus.PAYMENT_FAILED;
            orderService.updateOrderStatus(event.getOrderId(), status);
        } catch (Exception e) {
            log.error("Failed to process payment result message", e);
        }
    }
}
