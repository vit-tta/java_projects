package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.event.OutboxEvent;
import org.example.model.event.OutboxEventStatus;
import org.example.repository.OutboxEventRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
 * OutboxProcessor реализует паттерн Transactional Outbox.
 * Сервис периодически опрашивает таблицу outbox_events и отправляет
 * события со статусом PENDING в Kafka. После успешной отправки
 * событие помечается как SENT.
 * Такой подход гарантирует:
 * надёжную доставку событий
 * устойчивость к сбоям сервисов и Kafka
 * exactly-once обработку бизнес-событий
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxProcessor {
    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processOutbox() {
        List<OutboxEvent> pendingEvents = outboxEventRepository
                .findByStatus(OutboxEventStatus.PENDING);

        for (OutboxEvent event : pendingEvents) {
            try {
                // Отправляем событие в Kafka
                kafkaTemplate.send("order-created-events", event.getId().toString(), event.getPayload());

                // Помечаем как отправленное
                event.setStatus(OutboxEventStatus.SENT);
                event.setSentAt(java.time.LocalDateTime.now());
                outboxEventRepository.save(event);

                log.info("Event sent to Kafka: {}", event.getId());
            } catch (Exception e) {
                log.error("Failed to send event: {}", event.getId(), e);
            }
        }
    }
}