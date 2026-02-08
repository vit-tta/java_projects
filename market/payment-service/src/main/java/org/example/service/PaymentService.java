package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.event.OrderCreatedEvent;
import org.example.model.event.OutboxEventStatus;
import org.example.event.PaymentResultEvent;
import org.example.model.Account;
import org.example.model.event.OutboxEvent;
import org.example.model.Transaction;
import org.example.repository.AccountRepository;
import org.example.repository.OutboxEventRepository;
import org.example.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.UUID;
/**
 * PaymentService — основной бизнес-сервис платежей.
 * Ответственность сервиса:
 * 1. Идемпотентно обрабатывать события создания заказа (OrderCreatedEvent)
 * 2. Списывать деньги со счета пользователя
 * 3. Сохранять результат платежа
 * 4. Публиковать результат платежа через Transactional Outbox
 * Сервис гарантирует:
 * - exactly-once семантику списания средств
 * - корректную работу при повторной доставке сообщений из Kafka
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void processPayment(OrderCreatedEvent event) {
        UUID orderId = event.getOrderId();

        // Идемпотентность на уровне транзакций
        if (transactionRepository.existsByOrderId(orderId)) {
            log.info("Payment already processed for order: {}", orderId);
            return;
        }

        Account account = accountRepository.findByUserId(event.getUserId())
                .orElseThrow(() -> new RuntimeException("Account not found for user: " + event.getUserId()));

        boolean paymentSuccessful = false;

        try {
            // Проверяем и списываем средства с оптимистичной блокировкой
            if (account.hasSufficientFunds(event.getAmount())) {
                account.debit(event.getAmount());
                accountRepository.save(account);
                paymentSuccessful = true;
                log.info("Payment successful for order: {}", orderId);
            } else {
                log.warn("Insufficient funds for order: {}. Balance: {}, Required: {}",
                        orderId, account.getBalance(), event.getAmount());
            }
        } catch (Exception e) {
            log.error("Payment failed for order: {}", orderId, e);
        }

        // Сохраняем транзакцию
        Transaction transaction = new Transaction();
        transaction.setOrderId(orderId);
        transaction.setUserId(event.getUserId());
        transaction.setAmount(event.getAmount());
        transaction.setSuccessful(paymentSuccessful);
        transactionRepository.save(transaction);

        // Сохраняем событие в Outbox
        PaymentResultEvent resultEvent = PaymentResultEvent.builder()
                .orderId(orderId)
                .successful(paymentSuccessful)
                .build();

        try {
            String payload = objectMapper.writeValueAsString(resultEvent);

            OutboxEvent outboxEvent = new OutboxEvent();
            outboxEvent.setAggregateType("PAYMENT");
            outboxEvent.setAggregateId(orderId.toString());
            outboxEvent.setEventType(paymentSuccessful ? "PAYMENT_SUCCEEDED" : "PAYMENT_FAILED");
            outboxEvent.setPayload(payload);
            outboxEvent.setStatus(OutboxEventStatus.PENDING);

            outboxEventRepository.save(outboxEvent);
            log.info("Outbox event saved for payment result of order: {}", orderId);
        } catch (Exception e) {
            log.error("Failed to save outbox event for order: {}", orderId, e);
        }
    }

    @Transactional
    public void createAccount(Long userId) {
        if (accountRepository.existsByUserId(userId)) {
            throw new RuntimeException("Account already exists for user: " + userId);
        }

        Account account = new Account();
        account.setUserId(userId);
        accountRepository.save(account);
        log.info("Account created for user: {}", userId);
    }

    @Transactional
    public void topUpAccount(Long userId, BigDecimal amount) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found for user: " + userId));

        account.credit(amount);
        accountRepository.save(account);
        log.info("Account topped up for user: {}. Amount: {}", userId, amount);
    }

    public BigDecimal getBalance(Long userId) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found for user: " + userId));
        return account.getBalance();
    }
}