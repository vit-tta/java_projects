package org.example.model.event;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Entity InboxEvent реализует паттерн Transactional Inbox.
 * Назначение:
 *  - хранить входящие события из Kafka в базе данных
 *  - гарантировать идемпотентную обработку сообщений
 *  - обеспечить семантику exactly-once на уровне бизнес-логики
 * Каждое сообщение из Kafka сначала сохраняется в эту таблицу,
 * и только после успешной обработки помечается как processed=true.
 */
@Entity
@Table(name = "inbox_events")
@Data
public class InboxEvent {
    @Id
    private UUID id; // ID сообщения из Kafka

    @Column(nullable = false)
    private String aggregateType;

    @Column(nullable = false)
    private String aggregateId;

    @Column(nullable = false)
    private String eventType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String payload;

    @Column(nullable = false)
    private LocalDateTime receivedAt = LocalDateTime.now();

    @Column(nullable = false)
    private boolean processed = false;

    private LocalDateTime processedAt;

    @Column(name = "message_key")
    private String messageKey;
}
