package org.example.repository;

import org.example.model.event.OutboxEvent;
import org.example.model.event.OutboxEventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
/**
 * Репозиторий для работы с Outbox-событиями.
 * Используется в рамках паттерна Transactional Outbox:
 *  - события сначала сохраняются в БД в таблицу outbox_events
 *  - затем отдельный процесс (OutboxProcessor) читает их и публикует в Kafka
 */
@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
    List<OutboxEvent> findByStatus(OutboxEventStatus status);
}
