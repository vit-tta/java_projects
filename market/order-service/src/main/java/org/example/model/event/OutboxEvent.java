package org.example.model.event;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Сущность OutboxEvent — реализация паттерна Transactional Outbox.
 * Используется для надёжной публикации событий
 * Событие сначала сохраняется в БД в рамках той же транзакции,
 * что и бизнес-операция, а затем отдельный процесс
 * публикует его в Kafka.
 */
@Entity
@Table(name = "outbox_events")
@Data
public class OutboxEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String aggregateType;

    @Column(nullable = false)
    private String aggregateId;

    @Column(nullable = false)
    private String eventType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OutboxEventStatus status = OutboxEventStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime sentAt;
}
