package ru.hse.bank.service.input;

import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Класс для ввода данных. Паттерн Декоратор (Адаптация стандартного Scanner под нужды приложения)
 */
@Service
public class ScannerInputSystem implements InputSystem {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public int getInt(int min, int max) {
        while (true) {
            try {
                int num = scanner.nextInt();
                if (isValidInt(num, min, max)) return num;
                throw new InputMismatchException();
            } catch (InputMismatchException e) {
                System.out.println("Некорректное число! \n Попробуйте еще раз");
                scanner.next();
            }
        }
    }

    private boolean isValidInt(int value, int min, int max) {
        return value >= min && value <= max;
    }

    @Override
    public String getString() {
        return scanner.next().trim();
    }

    @Override
    public int getChoice(List<String> options) {
        System.out.println("Список вариантов: ");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + " - " + options.get(i));
        }
        System.out.println("Введите только цифру:");
        return getInt(1, options.size());
    }
}
