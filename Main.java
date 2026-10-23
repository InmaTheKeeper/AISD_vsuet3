import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Integer best = null;
    private static int[] bestCombo = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите лимит км:");
        int limit;
        try {
            limit = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: лимит должен быть числом.");
            return;
        }

        System.out.println("Введите количество городов для посещения:");
        int k;
        try {
            k = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: количество должно быть числом.");
            return;
        }

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
            List<Integer> chosen = new ArrayList<>();
            for (int idx : bestCombo) {
                chosen.add(cities[idx]);
            }
            System.out.println("Максимальная сумма: " + result);
            System.out.println("Города в комбинации: " + chosen);
        }
    }

    public static Integer chooseBestSum(int limit, int k, int[] cities) {
        if (cities == null || k <= 0 || k > cities.length) {
            return null;
        }
        best = null;
        bestCombo = null;
        int[] current = new int[k];
        combine(cities, k, 0, 0, 0, limit, current);
        return best;
    }

    private static void combine(int[] cities, int k, int start, int count,
                                int sum, int limit, int[] current) {
        if (sum > limit) return;
        if (count == k) {
            // Нашли новую лучшую комбинацию — запоминаем её индексы
            if (best == null || sum > best) {
                best = sum;
                bestCombo = Arrays.copyOf(current, current.length);
            }
            return;
        }
        for (int i = start; i <= cities.length - (k - count); i++) {
            current[count] = i;                     // запомнили индекс
            combine(cities, k, i + 1, count + 1, sum + cities[i], limit, current);
        }
    }
}