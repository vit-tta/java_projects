package org.example.repository;

import org.example.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
/**
 * Репозиторий для работы с аккаунтами пользователей.
 * Использует Spring Data JPA и предоставляет:
 * - стандартные операции (через JpaRepository)
 * - дополнительные методы поиска по userId
 * Этот репозиторий используется в Payment Service
 * для управления балансом пользователя.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
