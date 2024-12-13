package com.example;

import java.util.ArrayList;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import java.util.List;

public class TestSession {
    private final List<PuzzleGenerator.Puzzle> puzzles = new ArrayList<>();  // Список всех головоломок
    private final List<Boolean> answersCorrect = new ArrayList<>();  // Список правильных/неправильных ответов
    private int currentQuestionIndex = 0;  // Индекс текущего вопроса
    private int attemptsLeft = 2;  // Количество оставшихся попыток для текущего вопроса

    // Метод для добавления головоломки в сессию
    public void addPuzzle(PuzzleGenerator.Puzzle puzzle) {
        puzzles.add(puzzle);
    }

    // Метод для получения текущей головоломки
    public PuzzleGenerator.Puzzle getCurrentPuzzle() {
        if (currentQuestionIndex < puzzles.size()) {
            return puzzles.get(currentQuestionIndex);
        } else {
            return null;  // Если вопросов больше нет, возвращаем null
        }
    }

    // Метод для отметки ответа (правильный или нет)
    public void markAnswer(boolean isCorrect) {
        if (isCorrect) {
            answersCorrect.add(true);  // Записываем правильный ответ
            currentQuestionIndex++;  // Переходим к следующему вопросу
            attemptsLeft = 2;  // Сбросить количество попыток для следующего вопроса
        } else {
            attemptsLeft--;  // Уменьшаем количество оставшихся попыток
            if (attemptsLeft <= 0) {
                answersCorrect.add(false);  // Записываем неправильный ответ
                currentQuestionIndex++;  // Переходим к следующему вопросу
                attemptsLeft = 2;  // Сбросить количество попыток для следующего вопроса
            }
        }
    }

    // Метод для проверки, завершен ли тест
    public boolean isTestCompleted() {
        return currentQuestionIndex >= puzzles.size();
    }

    // Метод для получения всех головоломок в сессии
    public List<PuzzleGenerator.Puzzle> getPuzzles() {
        return puzzles;
    }

    // Метод для получения всех правильных ответов (или ошибок)
    public List<Boolean> getAnswersCorrect() {
        return answersCorrect;
    }

    // Метод для получения индекса текущего вопроса
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    // Метод для получения оставшегося количества попыток
    public int getAttemptsLeft() {
        return attemptsLeft;
    }
}