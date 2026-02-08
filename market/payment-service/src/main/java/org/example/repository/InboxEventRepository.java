package org.example.repository;

import org.example.model.event.InboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
/**
 * Репозиторий для работы с InboxEvent.
 * Используется в паттерне Transactional Inbox для:
 *  - хранения входящих сообщений из Kafka
 *  - проверки, было ли сообщение уже обработано
 * ключевой элемент для реализации идемпотентности
 * и семантики exactly-once на уровне бизнес-логики.
 */
@Repository
public interface InboxEventRepository extends JpaRepository<InboxEvent, UUID> {
    boolean existsById(UUID id);
}