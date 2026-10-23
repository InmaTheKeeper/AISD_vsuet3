import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static Integer best = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод лимита
        System.out.println("Введите лимит км:");
        int limit;
        try {
            limit = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: лимит должен быть числом.");
            return;
        }

        // Ввод k
        System.out.println("Введите количество городов для посещения:");
        int k;
        try {
            k = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: количество должно быть числом.");
            return;
        }

        // Ввод списка городов
        System.out.println("Введите расстояния через запятую:");
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            System.out.println("Ошибка: пустой ввод.");
            return;
        }

        int[] cities;
        try {
            cities = Arrays.stream(line.split(","))
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: список должен содержать числа через запятую.");
            return;
        }

        Integer result = chooseBestSum(limit, k, cities);
        if (result == null) {
            System.out.println("Нет подходящей комбинации.");
        } else {
            System.out.println("Максимальная сумма: " + result);
        }
    }

    public static Integer chooseBestSum(int limit, int k, int[] cities) {
        if (cities == null || k <= 0 || k > cities.length) {
            return null;
        }
        best = null;
        combine(cities, k, 0, 0, 0, limit);
        return best;
    }

    // Рекурсивный перебор сочетаний
    private static void combine(int[] cities, int k, int start, int count, int sum, int limit) {
        if (sum > limit) return;            // уже превысили — дальше нет смысла
        if (count == k) {                   // собрали ровно k городов
            if (best == null || sum > best) {
                best = sum;
            }
            return;
        }
        // Ограничиваем цикл: нужно ещё k - count городов после текущего
        for (int i = start; i <= cities.length - (k - count); i++) {
            combine(cities, k, i + 1, count + 1, sum + cities[i], limit);
        }
    }
}