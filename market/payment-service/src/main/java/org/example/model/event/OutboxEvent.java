package org.example.model.event;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Сущность OutboxEvent представляет событие,
 * сохранённое в outbox-таблице в рамках паттерна Transactional Outbox.
 * Outbox используется для надёжной отправки событий во внешний брокер
 * с семантикой at-least-once и возможностью добиться exactly-once
 * на уровне бизнес-логики.
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
