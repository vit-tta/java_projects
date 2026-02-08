package org.example.repository;

import org.example.model.event.OutboxEvent;
import org.example.model.event.OutboxEventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
/**
 * Репозиторий для работы с таблицей outbox-событий.
 * Используется в реализации паттерна Transactional Outbox:
 * событие сначала сохраняется в БД
 * затем отдельный процесс/шедулер отправляет его в Kafka
 * Гарантирует надёжную доставку событий
 * даже при сбоях сервисов.
 */
@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
    List<OutboxEvent> findByStatus(OutboxEventStatus status);
}
