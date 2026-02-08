package ru.hse.bank.service;

import ru.hse.bank.model.BankAccount;
import ru.hse.bank.storage.bankAccount.BankAccountStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Бизнес-логика работы с аккаунтами
 */
@Service
@AllArgsConstructor
public class BankAccountService {
    private final BankAccountStorage storage;

    public BankAccount createAccount(String name, int balance) {
        BankAccount account = BankAccount.builder()
                .name(name)
                .balance(balance)
                .build();
        return storage.save(account);
    }

    public List<BankAccount> getAllAccounts() {
        return storage.findAll();
    }

    public void deleteAccount(int id) {
        storage.delete(id);
    }

    public BankAccount updateAccount(int id, String name, Integer balance) {
        return storage.findById(id)
                .map(account -> {
                    if (name != null) account.setName(name);
                    if (balance != null) account.setBalance(balance);
                    return storage.save(account);
                })
                .orElseThrow(() -> new IllegalArgumentException("Счет не найден"));
    }
}
