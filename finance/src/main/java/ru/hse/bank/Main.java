package ru.hse.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.hse.bank.menu.MenuMain;

/**
 * Класс с точкой входа Main
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        MenuMain menu = applicationContext.getBean(MenuMain.class);
        menu.printMenu();
    }
}