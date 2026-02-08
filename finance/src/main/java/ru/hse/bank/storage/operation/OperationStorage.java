package ru.hse.bank.storage.operation;

import ru.hse.bank.model.Operation;

import java.util.List;
import java.util.Optional;

public interface OperationStorage {
    Operation save(Operation operation);
    Optional<Operation> findById(int id);
    List<Operation> findAll();
    List<Operation> findByBankAccountId(int bankAccountId);
    List<Operation> findByCategoryId(int categoryId);
    void delete(int id);
}
