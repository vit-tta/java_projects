package ru.hse.bank.storage.operation;

import ru.hse.bank.model.Operation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryOperationStorage implements OperationStorage {
    private final Map<Integer, Operation> operations = new LinkedHashMap<>();
    protected int nextId = 1;

    @Override
    public Operation save(Operation operation) {
        if (operation.getId() == null) {
            operation.setId(nextId++);
        }
        operations.put(operation.getId(), operation);
        return operation;
    }

    @Override
    public Optional<Operation> findById(int id) {
        return Optional.ofNullable(operations.get(id));
    }

    @Override
    public List<Operation> findAll() {
        return List.copyOf(operations.values());
    }

    @Override
    public List<Operation> findByBankAccountId(int bankAccountId) {
        return operations.values().stream()
                .filter(operation -> operation.getBankAccountId() == (bankAccountId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Operation> findByCategoryId(int categoryId) {
        return operations.values().stream()
                .filter(operation -> operation.getCategoryId() == (categoryId))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        operations.remove(id);
    }
}
