package ru.hse.bank.storage.bankAccount;

import ru.hse.bank.model.BankAccount;
import java.util.List;
import java.util.Optional;

public interface BankAccountStorage {
    BankAccount save(BankAccount account);
    Optional<BankAccount> findById(int id);
    List<BankAccount> findAll();
    void delete(int id);
}
