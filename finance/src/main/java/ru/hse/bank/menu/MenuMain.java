package ru.hse.bank.menu;

import ru.hse.bank.service.input.InputSystem;

import java.util.Arrays;
import java.util.LinkedList;

import org.springframework.stereotype.Component;

/**
 * Класс с основным меню
 */
@Component
public class MenuMain {
    private final InputSystem inputSystem; // паттерн Делегат
    private final LinkedList<MenuItem> baseMenu;
    private final LinkedList<MenuItem> baseCategory;
    private final LinkedList<MenuItem> baseAccount;
    private final LinkedList<MenuItem> baseOperation;

    public MenuMain(InputSystem inputSystem, CategoryMenu categoryMenu, BankAccountMenu bankAccountMenu, OperationMenu operationMenu) {
        this.inputSystem = inputSystem;
        baseCategory = new LinkedList<>(
                Arrays.asList(
                        addMenuItem("Добавить новую категорию.", categoryMenu::addCategory),
                        addMenuItem("Редактировать существующие категории.", categoryMenu::changeCategory),
                        addMenuItem("Удалить категорию.", categoryMenu::deleteCategory),
                        addMenuItem("Посмотреть все категории.", categoryMenu::showCategories),
                        addMenuItem("Назад к основному меню.", this::printMenu)
                )
        );
        baseAccount = new LinkedList<>(
                Arrays.asList(
                        addMenuItem("Добавить новый счет.", bankAccountMenu::addBankAccount),
                        addMenuItem("Редактировать существующие счета.", bankAccountMenu::changeBankAccount),
                        addMenuItem("Удалить счет.", bankAccountMenu::deleteBankAccount),
                        addMenuItem("Посмотреть все счета.", bankAccountMenu::showAccounts),
                        addMenuItem("Назад к основному меню.", this::printMenu)
                )
        );
        baseOperation = new LinkedList<>(
                Arrays.asList(
                        addMenuItem("Добавить новую операцию.", operationMenu::addOperation),
                        addMenuItem("Редактировать существующие операции.", operationMenu::changeOperation),
                        addMenuItem("Удалить операцию.", operationMenu::deleteOperation),
                        addMenuItem("Посмотреть все операции.", operationMenu::showOperations),
                        addMenuItem("Назад к основному меню.", this::printMenu)
                )
        );
        baseMenu = new LinkedList<>(
                Arrays.asList(
                        addMenuItem("Управление категориями.", this::showListOfBaseFunctionsWithCategory),
                        addMenuItem("Управление аккаунтами.", this::showListOfBaseFunctionsWithAccount),
                        addMenuItem("Управление операциями.", this::showListOfBaseFunctionsWithOperation),
                        addMenuItem("Выход.", () -> System.exit(0))
                )
        );
    }

    public void printMenu() {
        System.out.println("Добро пожаловать в Учет Финансов!");
        start(baseMenu);
    }

    public void start(LinkedList<MenuItem> ls) {
        while (true) {
            printMenu(ls);
            int input = inputSystem.getInt(1, ls.size());
            ls.get(input - 1).action.run();
        }
    }

    private void showListOfBaseFunctionsWithAccount() {
        start(baseAccount);
    }

    private void showListOfBaseFunctionsWithOperation() {
        start(baseOperation);
    }

    private void showListOfBaseFunctionsWithCategory() {
        start(baseCategory);
    }

    public void printMenu(LinkedList<MenuItem> ls) {
        System.out.println("Меню: ");
        for (int i = 0; i < ls.size(); i++) {
            System.out.println((i + 1) + ") " + ls.get(i).description);
        }
    }

    private MenuItem addMenuItem(String description, Runnable action) {
        return new MenuItem(description, action);
    }

    private record MenuItem(String description, Runnable action) {
    }
}


