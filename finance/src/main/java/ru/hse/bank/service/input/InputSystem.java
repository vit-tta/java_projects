package ru.hse.bank.service.input;

import java.util.List;

/**
 * Интерфейс для работы с вводом с клавиатуры
 */
public interface InputSystem {
    int getInt(int min, int max);

    String getString();

    int getChoice(List<String> choices);
}
