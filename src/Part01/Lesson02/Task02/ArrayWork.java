package Part01.Lesson02.Task02;


import java.util.Random;

/**
 * Работа с массивом
 */
public class ArrayWork {

    /**
     * Создает массив
     *
     * @param size, min, max
     * @return
     */
    public int[] numbersList(int size, int min, int max) {
        int[] numbers = new int[size];
        Random random = new Random();
        int diff = max - min;

        for (int i = 0; i < size; i++) {
            int j = random.nextInt(diff + 1);
            j += min;
            numbers[i] = j;
        }

        return numbers;
    }

    /**
     * Находит корни
     *
     * @param array
     * @throws NumberException
     */
    public void sqrt(int[] array) throws NumberException {
        for (int i = 0; i < array.length; i++) {
            try {
                if (array[i] < 0) {
                    throw new NumberException();
                }
                int k = (int) Math.sqrt(array[i]);

                if ((k * k) == array[i]) {
                    System.out.println(array[i]);
                }
            } catch (NumberException e) {
                e.printStackTrace();
                System.out.println(array[i] + " является отрицательным числом, корень не находится");
            }
        }
    }
}
