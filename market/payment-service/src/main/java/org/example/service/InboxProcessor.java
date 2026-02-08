package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.event.OrderCreatedEvent;
import org.example.model.event.InboxEvent;
import org.example.repository.InboxEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
/**
 * InboxProcessor — реализация паттерна Transactional Inbox.
 * Назначение:
 * 1. Получать события из Kafka (order-created-events)
 * 2. Гарантировать идемпотентность обработки (exactly-once на уровне бизнес-логики)
 * 3. Обеспечивать корректную обработку платежей даже при повторной доставке сообщений
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InboxProcessor {
    private final InboxEventRepository inboxEventRepository;
    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-created-events", groupId = "payment-service")
    @Transactional
    public void consumeOrderCreatedEvent(String messageKey, String payload) {
        log.info("Received Kafka message: key={}, payload={}", messageKey, payload);

        try {
            // Генерируем свой UUID для идемпотентности
            UUID inboxEventId = UUID.randomUUID();
            log.info("Generated inboxEventId: {}", inboxEventId);

            // Парсим событие
            OrderCreatedEvent event = objectMapper.readValue(payload, OrderCreatedEvent.class);
            log.info("Parsed OrderCreatedEvent: orderId={}, userId={}, amount={}",
                    event.getOrderId(), event.getUserId(), event.getAmount());

            // Проверяем идемпотентность по orderId (а не по messageKey)
            // Используем orderId для проверки дубликатов
            UUID orderId = event.getOrderId();

            // Сохраняем во Inbox
            InboxEvent inboxEvent = new InboxEvent();
            inboxEvent.setId(inboxEventId);  // Генерируем свой UUID
            inboxEvent.setAggregateType("ORDER");
            inboxEvent.setAggregateId(orderId.toString());
            inboxEvent.setEventType("ORDER_CREATED");
            inboxEvent.setPayload(payload);
            inboxEvent.setMessageKey(messageKey);  // Сохраняем оригинальный ключ если нужно

            inboxEventRepository.save(inboxEvent);
            log.info("Inbox event saved with id: {}", inboxEventId);

            // Обрабатываем платеж
            try {
                paymentService.processPayment(event);
                log.info("Payment processed for order: {}", orderId);
            } catch (Exception e) {
                log.error("Payment processing failed for order: {}", orderId, e);
                throw e;
            }

            // Помечаем как обработанное
            inboxEvent.setProcessed(true);
            inboxEvent.setProcessedAt(java.time.LocalDateTime.now());
            inboxEventRepository.save(inboxEvent);

            log.info("Event marked as processed: {}", inboxEventId);

        } catch (Exception e) {
            log.error("Failed to process order created event. key={}, payload={}",
                    messageKey, payload, e);
            throw new RuntimeException("Failed to process payment", e);
        }
    }
}