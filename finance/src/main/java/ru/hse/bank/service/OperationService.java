package ru.hse.bank.service;

import ru.hse.bank.model.Operation;
import ru.hse.bank.model.OperationType;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.storage.bankAccount.BankAccountStorage;
import ru.hse.bank.storage.operation.OperationStorage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
/**
 * Бизнес-логика работы с операциями
 */
@Service
public class OperationService {
    private final OperationStorage operationStorage;
    private final BankAccountStorage bankAccountStorage;

    public OperationService(OperationStorage operationStorage, BankAccountStorage bankAccountStorage) {
        this.operationStorage = operationStorage;
        this.bankAccountStorage = bankAccountStorage;
    }

    public Operation createOperation(OperationType type, int bankAccountId, int amount, LocalDate date,
                                     String description, Integer categoryId) {

        var account = bankAccountStorage.findById(bankAccountId).orElseThrow(() -> new IllegalArgumentException("Счет не найден"));

        Operation operation = Operation.builder()
                .type(type)
                .bankAccountId(bankAccountId)
                .amount(amount)
                .date(date)
                .description(description)
                .categoryId(categoryId)
                .build();

        Operation savedOperation = operationStorage.save(operation);
        updateAccountBalance(account.getId(), type, amount);

        return savedOperation;
    }

    public List<Operation> getAll() {
        return operationStorage.findAll();
    }

    public void updateAccountBalance(int id, OperationType type, int amount) {

        BankAccount account = bankAccountStorage.findById(id)
                .orElseThrow(RuntimeException::new);
        int currentBalance = account.getBalance();
        int newBalance;

        if (type == OperationType.INCOME) {
            newBalance = currentBalance + amount;
        } else {
            if (currentBalance < amount) {
                throw new IllegalArgumentException("Недостаточно средств на счете");
            }
            newBalance = currentBalance - amount;
        }

        account.setBalance(newBalance);
        bankAccountStorage.save(account);
    }


    public void deleteOperation(Integer operationId) {
        Operation operation = operationStorage.findById(operationId).orElseThrow(() -> new IllegalArgumentException("Операция не найдена"));
        var account = bankAccountStorage.findById(operation.getBankAccountId()).orElseThrow(() -> new IllegalArgumentException("Счет не найден"));
        rollbackAccountBalance(account, operation.getType(), operation.getAmount());
        operationStorage.delete(operationId);
    }

    private void rollbackAccountBalance(BankAccount account, OperationType type, int amount) {
        int currentBalance = account.getBalance();
        int newBalance;

        if (type == OperationType.INCOME) {
            newBalance = currentBalance - amount;
        } else {
            newBalance = currentBalance + amount;
        }
        account.setBalance(newBalance);
        bankAccountStorage.save(account);
    }
}
