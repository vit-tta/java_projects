package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Сущность Transaction — представляет финансовую транзакцию,
 * связанную с оплатой конкретного заказа.
 * Используется в Payment Service для:
 * - фиксации факта списания денег
 * - обеспечения идемпотентности (один заказ = одна транзакция)
 */
@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_id", nullable = false, unique = true)
    private UUID orderId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private boolean successful;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
