package ru.hse.bank.model;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * Класс для счета (аккаунта)
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class BankAccount {
    private int id;
    private String name;
    private int balance;

    @Override
    public String toString(){
        return "BankAccount{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
