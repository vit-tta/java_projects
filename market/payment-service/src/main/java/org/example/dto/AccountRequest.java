package org.example.dto;

import lombok.Data;
import java.math.BigDecimal;
/**
 * DTO для операций с аккаунтом пользователя.
 * Используется при пополнении баланса.
 */
@Data
public class AccountRequest {
    private BigDecimal amount;
}
