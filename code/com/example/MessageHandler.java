package com.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

public class MessageHandler {

    private final PuzzleGenerator puzzleGenerator = new PuzzleGenerator();
    private final Map<Long, PuzzleGenerator.Puzzle> userPuzzles = new HashMap<>();
    private final Map<Long, String> userDifficulty = new HashMap<>(); // Для хранения уровня сложности
    private final Map<Long, Boolean> awaitingDifficulty = new HashMap<>(); // Флаг ожидания выбора сложности
    private final Map<Long, TestSession> userTestSessions = new HashMap<>();

    // Метод для обработки сообщения
    public SendMessage handleMessage(Update update) {
        String messageText = update.getMessage().getText().trim();  // Убираем лишние пробелы
        long chatId = update.getMessage().getChatId();

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));

        // Логируем полученное сообщение и id чата
        System.out.println("Received message: '" + messageText + "' from Chat ID: " + chatId);

        // Логика обработки сообщений
        switch (messageText) {
            case "/start":
                message.setText("Привет! Я бот с логическими задачами. Напиши /puzzle, чтобы получить головоломку.\n\n" +
                        "Если хочешь открыть меню или тебе нужна помощь, напиши /help.");
                awaitingDifficulty.put(chatId, false); // Сбрасываем флаг ожидания выбора уровня
                break;

            case "/test":
                startTest(chatId);
                message.setText("Тест начался! Вот первый вопрос:\n\n" + userTestSessions.get(chatId).getCurrentPuzzle().getQuestion());
                break;

            case "/puzzle":
                message.setText("Выберите уровень сложности:\n1. Легкий\n2. Средний\n3. Сложный\nНапишите номер уровня.");
                awaitingDifficulty.put(chatId, true); // Устанавливаем флаг ожидания выбора уровня
                break;

            case "/help":
                message.setText("Доступные команды:\n/start - Запуск бота\n/puzzle - Получить головоломку\n/answer - Узнать ответ на головоломку\n/help - Справка\n/test - Начать тест");
                break;

            case "/answer":
                if (userPuzzles.containsKey(chatId)) {
                    PuzzleGenerator.Puzzle puzzle = userPuzzles.get(chatId);
                    message.setText("Правильный ответ: " + puzzle.getAnswer() + "\n\nНапишите /puzzle, чтобы получить новую головоломку.");
                    userPuzzles.remove(chatId); // Удаляем текущую головоломку после показа ответа
                } else {
                    message.setText("У вас нет активной головоломки. Напишите /puzzle, чтобы получить новую головоломку.");
                }
                break;

            default:
                // Если ожидается выбор сложности
                if (awaitingDifficulty.getOrDefault(chatId, false) && (messageText.equals("1") || messageText.equals("2") || messageText.equals("3"))) {
                    String difficulty = getDifficultyFromChoice(messageText);

                    if (difficulty != null) {
                        userDifficulty.put(chatId, difficulty);
                        PuzzleGenerator.Puzzle puzzle = puzzleGenerator.getRandomPuzzle(difficulty);
                        userPuzzles.put(chatId, puzzle);
                        message.setText("Головоломка:\n" + puzzle.getQuestion() + "\n\nНапишите ваш ответ.");
                        awaitingDifficulty.put(chatId, false); // Сбрасываем флаг после выбора уровня
                    }
                }
                // Проверяем ответ пользователя, если он активен
                else if (userPuzzles.containsKey(chatId)) {
                    PuzzleGenerator.Puzzle puzzle = userPuzzles.get(chatId);
                    if (messageText.equalsIgnoreCase(puzzle.getAnswer())) {
                        message.setText("Поздравляем! Ваш ответ верный 🎉\nНапишите /puzzle, чтобы получить новую головоломку.");
                        userPuzzles.remove(chatId);
                    } else {
                        message.setText("Неправильно! Попробуйте ещё раз или напишите /answer, чтобы узнать правильный ответ.");
                    }
                }
                // Обработка теста
                else if (userTestSessions.containsKey(chatId)) {
                    handleTestAnswer(chatId, messageText, message);
                }
                // Если команда не распознана
                else {
                    message.setText("Я не понимаю эту команду. Напишите /help для списка команд.");
                }
                break;
        }

        return message;
    }


    // Метод для получения уровня сложности из выбранного числа
    private String getDifficultyFromChoice(String choice) {
        switch (choice) {
            case "1": return "easy";
            case "2": return "medium";
            case "3": return "hard";
            default: return null;
        }
    }
    private void startTest(long chatId) {
        TestSession testSession = new TestSession();

        // Добавляем 3 легких, 3 средних и 2 сложных вопроса
        testSession.addPuzzle(puzzleGenerator.getRandomPuzzle("easy"));
        testSession.addPuzzle(puzzleGenerator.getRandomPuzzle("easy"));
        testSession.addPuzzle(puzzleGenerator.getRandomPuzzle("easy"));
        testSession.addPuzzle(puzzleGenerator.getRandomPuzzle("medium"));
        testSession.addPuzzle(puzzleGenerator.getRandomPuzzle("medium"));
        testSession.addPuzzle(puzzleGenerator.getRandomPuzzle("medium"));
        testSession.addPuzzle(puzzleGenerator.getRandomPuzzle("hard"));
        testSession.addPuzzle(puzzleGenerator.getRandomPuzzle("hard"));

        userTestSessions.put(chatId, testSession);
    }
    private void handleTestAnswer(long chatId, String messageText, SendMessage message) {
        TestSession session = userTestSessions.get(chatId);
        PuzzleGenerator.Puzzle currentPuzzle = session.getCurrentPuzzle();

        if (messageText.equalsIgnoreCase(currentPuzzle.getAnswer())) {
            session.markAnswer(true);
            if (session.isTestCompleted()) {
                message.setText("Правильно! Тест завершён.\n\n" + getTestStatistics(session));
                userTestSessions.remove(chatId);
            } else {
                message.setText("Правильно!\nСледующий вопрос:\n\n" + session.getCurrentPuzzle().getQuestion());
            }
        } else {
            session.markAnswer(false);
            if (session.isTestCompleted()) {
                message.setText("Ответ неверный. Тест завершён.\n\n" + getTestStatistics(session));
                userTestSessions.remove(chatId);
            } else {
                if (session.getAttemptsLeft() == 2) {
                    message.setText("Ответ неверный. Следующий вопрос:\n\n" + session.getCurrentPuzzle().getQuestion());
                } else {
                    message.setText("Неправильно. Попробуйте снова. Осталось попыток: " + session.getAttemptsLeft());
                }
            }
        }
    }
    private String getTestStatistics(TestSession session) {
        int correctAnswers = (int) session.getAnswersCorrect().stream().filter(Boolean::booleanValue).count();
        int totalQuestions = session.getPuzzles().size();

        StringBuilder result = new StringBuilder();
        result.append("Вы правильно ответили на ").append(correctAnswers).append(" из ").append(totalQuestions).append(" вопросов.\n");

        if (correctAnswers <= 2) {
            result.append("Не расстраивайтесь, обязательно получится в следующий раз!");
        } else if (correctAnswers <= 4) {
            result.append("Хороший результат! Продолжайте в том же духе!");
        } else {
            result.append("Отлично справились! Молодец!");
        }

        return result.toString();
    }

}