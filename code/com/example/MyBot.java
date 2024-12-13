package com.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyBot extends TelegramLongPollingBot {
    private final MessageHandler messageHandler = new MessageHandler();

    // Метод для обработки сообщений
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            SendMessage response = messageHandler.handleMessage(update);
            try {
                execute(response); 
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String getBotUsername() {
        return "BestGameBotEverBot"; 
    }

    // Возвращает токен бота
    @Override
    public String getBotToken() {
        return "777"; // токен
    }
}
