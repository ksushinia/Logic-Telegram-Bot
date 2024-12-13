package com.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // Запуск бота
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyBot());
            System.out.println("Бот запущен!");

            // Добавляем обработку выхода из программы по нажатию клавиши
            Scanner scanner = new Scanner(System.in);
            System.out.println("Нажмите 'q' для выхода из программы.");

            // Ожидаем ввода 'q' для завершения работы
            while (true) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("q")) {
                    System.out.println("Выход из программы...");
                    break;
                }
            }

            // Завершаем выполнение бота
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
