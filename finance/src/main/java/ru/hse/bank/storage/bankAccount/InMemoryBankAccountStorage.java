package ru.hse.bank.storage.bankAccount;

import ru.hse.bank.model.BankAccount;

import java.util.*;


public class InMemoryBankAccountStorage implements BankAccountStorage {
    private final Map<Integer, BankAccount> accounts = new LinkedHashMap<>();
    protected int nextId = 1;

    @Override
    public BankAccount save(BankAccount account) {
        if (account.getId() == 0) {
            account.setId(nextId++);
        }
        accounts.put(account.getId(), account);
        return account;
    }

    @Override
    public Optional<BankAccount> findById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    @Override
    public List<BankAccount> findAll() {
        return List.copyOf(accounts.values());
    }

    @Override
    public void delete(int id) {
        accounts.remove(id);
    }
}
