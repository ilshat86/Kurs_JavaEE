package Part01.Lesson02.Task01;

/**
 * Основной класс
 */
public class Main {
    /**
     * Основной метод
     *
     * @param args String[]
     * @throws MyExcept
     */
    public static void main(String[] args) throws MyExcept {
        String s = "Hello, World!";

        Object obj = null;

        try {
            if (!obj.equals(s)) {
                System.err.println("This may result in NullPointerException if unknownObject is null");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("выводится ошибка NullPointerException при сравнении null и строки");

        } finally {
            System.out.println(s);
        }

        if (!s.equals(obj)) {
            System.out.println("А строку и null можно сравнивать");
        }

        char[] ch = s.toCharArray();

        try {
            for (int i = 0; i < 20; i++) {
                System.out.println(ch[i]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("вышли за пределы длины строки(массива)");
        } finally {
            System.out.println(s);
        }

        try {

            throw new MyExcept();
        } catch (MyExcept e) {
            e.printStackTrace();
        }
    }
}
