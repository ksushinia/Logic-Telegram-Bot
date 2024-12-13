package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PuzzleGenerator {

    private final List<Puzzle> easyPuzzles;
    private final List<Puzzle> mediumPuzzles;
    private final List<Puzzle> hardPuzzles;
    private final Random random = new Random();

    public PuzzleGenerator() {
        easyPuzzles = new ArrayList<>();
        mediumPuzzles = new ArrayList<>();
        hardPuzzles = new ArrayList<>();

        // Легкие задачи
        easyPuzzles.add(new Puzzle("Продолжите последовательность: 2, 4, 8, 16, 32, ... (в ответе укажите число)", "64"));
        easyPuzzles.add(new Puzzle("Продолжите последовательность: 1, 4, 9, 16, 25, ... (в ответе укажите число)", "36"));
        easyPuzzles.add(new Puzzle("Сколько кубиков в основании пирамиды, состоящей из 5 слоёв, если каждый слой — квадрат? (в ответе укажите число)", "25"));
        easyPuzzles.add(new Puzzle("Сейчас отцу 45 лет, а сыну 15 лет. Через сколько лет возраст отца будет вдвое больше возраста сына? (в ответе укажите число)", "15"));
        easyPuzzles.add(new Puzzle("Сколько натуральных делителей у числа 36? (в ответе укажите число)", "8"));
        easyPuzzles.add(new Puzzle("Сколько цифр \"7\" встречается в числах от 1 до 100? (в ответе укажите число)", "20"));
        easyPuzzles.add(new Puzzle("Чему равна разница квадратов чисел 15 и 10? (в ответе укажите число)", "125"));
        easyPuzzles.add(new Puzzle("Найдите наименьшее общее кратное чисел 6 и 8. (в ответе укажите число)", "24"));
        easyPuzzles.add(new Puzzle("Площадь прямоугольника в два раза больше площади квадрата. Сторона квадрата равна 4 см. Чему равна длина прямоугольника, если его ширина равна 4 см? (в ответе укажите число)", "8"));

        // Средние задачи
        mediumPuzzles.add(new Puzzle("Продолжите последовательность: 1, 1, 2, 3, 5, 8, ... (в ответе укажите число)", "13"));
        mediumPuzzles.add(new Puzzle("Продолжите последовательность: 1, 2, 6, 24, 120, ... (в ответе укажите число)", "720"));
        mediumPuzzles.add(new Puzzle("Продолжите последовательность: 5, 10, 20, 40, 80, ... (в ответе укажите число)", "160"));
        mediumPuzzles.add(new Puzzle("Какое наименьшее число делится на 15, 20 и 25? (в ответе укажите число)",  "300"));
        mediumPuzzles.add(new Puzzle("В мешке 3 белых и 2 чёрных мяча. Какова вероятность того, что при случайном извлечении двух мячей оба будут белыми? (в ответе укажите число)",  "0,3"));
        mediumPuzzles.add(new Puzzle("На сколько частей можно разделить циферблат часов тремя прямыми линиями? (в ответе укажите число)",  "7"));
        mediumPuzzles.add(new Puzzle("Найдите четыре последовательных числа, сумма которых равна 30. (в ответе укажите числа в виде 1, 2, 3, 4)",  "6, 7, 8, 9"));
        mediumPuzzles.add(new Puzzle("Найдите три последовательных числа, сумма которых равна 66. (в ответе укажите числа в виде 1, 2, 3)",  "21, 22, 23"));
        mediumPuzzles.add(new Puzzle("Есть куб, каждая сторона которого окрашена. Если куб разрезать на 27 маленьких кубиков (3x3x3), сколько из них будут окрашены только с одной стороны? (в ответе укажите число)",  "6"));

        // Сложные задачи
        hardPuzzles.add(new Puzzle("Какое следующее число в ряду: 1, 4, 27, 256, ...? (в ответе укажите число)", "3125"));
        hardPuzzles.add(new Puzzle("Какое число должно идти следующим: 1, 11, 21, 1211, 111221, ...? (в ответе укажите число)", "312211"));
        hardPuzzles.add(new Puzzle("Сколько раз в сутки часовая и минутная стрелки часов совпадают? (в ответе укажите число)", "22"));
        hardPuzzles.add(new Puzzle("Какое самое маленькое число делится на 1, 2, 3, 4, 5, 6, 7, 8, 9 и 10? (в ответе укажите число)", "2520"));
        hardPuzzles.add(new Puzzle("Какое число получится, если сложить все числа от 1 до 100? (в ответе укажите число)", "5050"));
        hardPuzzles.add(new Puzzle("Сколько раз в сутки минутная стрелка оказывается под прямым углом (90°) к часовой стрелке? (в ответе укажите число)", "44"));
        hardPuzzles.add(new Puzzle("Найдите наименьшее число, которое при делении на 3 даёт остаток 2, при делении на 4 даёт остаток 3, а при делении на 5 даёт остаток 4 (в ответе укажите число)", "59"));
        hardPuzzles.add(new Puzzle("Какое минимальное количество взвешиваний нужно, чтобы определить, какая из 9 одинаковых монет фальшивая, если она легче остальных? (в ответе укажите число)", "2"));
        hardPuzzles.add(new Puzzle("Число состоит из двух цифр. Известно, что разность между цифрами числа составляет 3, а число в два раза больше суммы цифр. Какое это число? (в ответе укажите число)", "52"));
    }

    // Метод для случайного выбора головоломки в зависимости от уровня сложности
    public Puzzle getRandomPuzzle(String difficulty) {
        List<Puzzle> puzzles;

        switch (difficulty) {
            case "easy":
                puzzles = easyPuzzles;
                break;
            case "medium":
                puzzles = mediumPuzzles;
                break;
            case "hard":
                puzzles = hardPuzzles;
                break;
            default:
                return null;
        }

        if (puzzles.isEmpty()) {
            return null; // Если задачи закончились
        }

        int index = random.nextInt(puzzles.size());
        return puzzles.remove(index); // Удаляем задачу после выбора
    }
    public Puzzle getUniqueRandomPuzzle(String difficulty, List<Puzzle> usedPuzzles) {
        List<Puzzle> availablePuzzles;

        // Выбираем список в зависимости от сложности
        switch (difficulty) {
            case "easy":
                availablePuzzles = new ArrayList<>(easyPuzzles);
                break;
            case "medium":
                availablePuzzles = new ArrayList<>(mediumPuzzles);
                break;
            case "hard":
                availablePuzzles = new ArrayList<>(hardPuzzles);
                break;
            default:
                return null;
        }

        // Исключаем уже использованные вопросы
        availablePuzzles.removeAll(usedPuzzles);

        // Если все вопросы использованы, возвращаем null
        if (availablePuzzles.isEmpty()) {
            return null;
        }

        // Выбираем случайный вопрос из оставшихся
        Random random = new Random();
        return availablePuzzles.get(random.nextInt(availablePuzzles.size()));
    }

    public static class Puzzle {
        private final String question;
        private final String answer;

        public Puzzle(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }
    }
}
