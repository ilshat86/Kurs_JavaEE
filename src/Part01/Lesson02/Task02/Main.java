package Part01.Lesson02.Task02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Основной класс
 */
public class Main {

    /**
     * Работает с массивом
     *
     * @param args
     * @throws NumberException
     * @throws IOException
     */
    public static void main(String[] args) throws NumberException, IOException {
       System.out.println("Введите размер массива");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = reader.readLine();
        System.out.println("Введите минимальное значение в массиве");
        String s_min = reader.readLine();
        System.out.println("Введите максимальное значение в массиве");
        String s_max = reader.readLine();
        int n = 0;
        int Min =0;
        int Max =0;

        try {
            n = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Введена строка вместо числа!");
        }
        try {
            Min = Integer.parseInt(s_min);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("В минимальное значение введена строка вместо числа!");
        }
        try {
            Max = Integer.parseInt(s_max);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("В максимальное значение введена строка вместо числа!");
        }

        if (n < 0) {
            throw new NumberException("Введено отрицательное число!");
        }

        ArrayWork at = new ArrayWork();
        int[] arr     = at.numbersList(n,Min,Max);

        for (int value : arr) {
            System.out.println(value);
        }

        System.out.println("начинаем находить корни");
        at.sqrt(arr);
    }
}