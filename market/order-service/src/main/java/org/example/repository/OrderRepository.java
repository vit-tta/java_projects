package org.example.repository;

import org.example.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * Репозиторий для работы с сущностью {Order}.
 * Использует Spring Data JPA и предоставляет:
 * базовые операции
 * пользовательские методы поиска заказов
 * Репозиторий используется сервисным слоем {OrderService}.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(Long userId);
    Optional<Order> findByIdAndUserId(UUID id, Long userId);
}
