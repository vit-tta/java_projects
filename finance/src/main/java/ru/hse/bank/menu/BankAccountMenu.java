package ru.hse.bank.menu;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.service.BankAccountService;
import ru.hse.bank.service.input.InputSystem;


import java.util.List;

/**
 * Класс с меню, который отвечает за аккауты
 */
@Component
@AllArgsConstructor
public class BankAccountMenu {
    private final BankAccountService bankAccountService;
    private final InputSystem inputSystem;

    public void addBankAccount() {
        System.out.println("Вы Создаете новый аккаунт:");
        BankAccount bankAccount = formBankAccount();
        try {
            bankAccountService.createAccount(bankAccount.getName(), bankAccount.getBalance());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showAccounts() {
        System.out.println("Список всех аккаунтов: ");
        List<BankAccount> accounts = bankAccountService.getAllAccounts();
        for (BankAccount bankAccount : accounts) {
            System.out.println(bankAccount);
        }
    }

    public void changeBankAccount() {
        System.out.println("Вы Редактируете аккаунт:");
        System.out.println("Введите id аккаунта.");
        int id = inputSystem.getInt(0, Integer.MAX_VALUE);
        BankAccount bankAccount = formBankAccount();
        try {
            bankAccountService.updateAccount(id, bankAccount.getName(), bankAccount.getBalance());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteBankAccount() {
        System.out.println("Введите id категории: ");
        int id = inputSystem.getInt(1, Integer.MAX_VALUE);
        try {
            bankAccountService.deleteAccount(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private BankAccount formBankAccount() {
        System.out.println("Введите имя владельца счета: ");
        String name = inputSystem.getString();
        System.out.println("Введите начальный баланс: ");
        int balance = inputSystem.getInt(0, Integer.MAX_VALUE);
        BankAccount bankAccount = BankAccount.builder()
                .name(name)
                .balance(balance)
                .build();
        return bankAccount;
    }
}