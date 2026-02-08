package ru.hse.bank.menu;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hse.bank.model.Operation;
import ru.hse.bank.model.OperationType;
import ru.hse.bank.service.input.InputSystem;
import ru.hse.bank.service.OperationService;

import java.util.List;

/**
 * Класс с меню, который отвечает за операции
 */
@Component
@AllArgsConstructor
public class OperationMenu {
    private final OperationService operationService;
    private final InputSystem inputSystem;

    public void addOperation() {
        System.out.println("Вы Создаете новую операцию:");
        Operation operation = formOperation();
        try {
            operationService.createOperation(operation.getType(), operation.getBankAccountId(), operation.getAmount(), operation.getDate(), operation.getDescription(), operation.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showOperations() {
        System.out.println("Список всех операций: ");
        List<Operation> accounts = operationService.getAll();
        for (Operation operation : accounts) {
            System.out.println(operation);
        }
    }

    public void changeOperation() {
        System.out.println("Вы Редактируете операцию:");
        Operation operation = formOperation();
        try {
            operationService.updateAccountBalance(operation.getBankAccountId(), operation.getType(), operation.getAmount());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteOperation() {
        System.out.println("Введите id категории: ");
        int id = inputSystem.getInt(1, Integer.MAX_VALUE);
        try {
            operationService.deleteOperation(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Operation formOperation() {
        OperationType categoryType = inputSystem.getChoice(List.of("Прибыль", "Трата")) == 1
                ? OperationType.INCOME : OperationType.EXPENSE;
        System.out.println("Введите описание: ");
        String desc = inputSystem.getString();
        System.out.println("Введите id аккаунта: ");
        int bank_account_id = inputSystem.getInt(1, Integer.MAX_VALUE);
        System.out.println("Введите id категории: ");
        int category_id = inputSystem.getInt(1, Integer.MAX_VALUE);
        System.out.println("Введите сумму: ");
        int balance = inputSystem.getInt(1, Integer.MAX_VALUE);
        Operation operation = Operation.builder()
                .amount(balance)
                .description(desc)
                .categoryId(category_id)
                .bankAccountId(bank_account_id)
                .type(categoryType)
                .build();
        return operation;
    }
}
