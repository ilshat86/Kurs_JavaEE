package Part01.Lesson05.Task02;

import java.util.Random;
import java.io.*;

/**
 * Основной класс
 */
public class Main {

    /**
     * Основной метод
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        String[] library = Words.createWordsArray();

        getFiles("./src/Part01/Lesson05/Task02/Data/", 5, 1024, library, 10);
    }

    /**
     * Метод создает в определенном каталоге файлы переданного размера и записывает в них массив чисел
     * @param path
     * @param n
     * @param size
     * @param words
     * @param probability
     * @throws FileNotFoundException
     */
    static void getFiles(String path, int n, int size, String[] words, int probability) throws FileNotFoundException {

        Random random = new Random();

        StringBuilder text = Words.createText();
        String str = String.valueOf(text);
        String[] wordsFromText = str.split(" ");

        int changingWords = wordsFromText.length % probability;

        for (int i = 0; i < changingWords; i++) {
            int randomWordIndex = random.nextInt(wordsFromText.length - 1);
            wordsFromText[randomWordIndex] = words[random.nextInt(words.length - 1)];
        }

        String newText = String.join(" ", wordsFromText);

        for (int i = 0; i < n; i++) {
            String s = "tmp" + i;
            s = s+".txt";
            File newFile = new File(path, s);
            if (newFile.canWrite()) {
                FileOutputStream fos = new FileOutputStream(newFile, false);
                byte[] buffer = newText.getBytes();

                try {

                    fos.write(buffer, 0, size);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Сформировано "+ n + " файлов");
    }
}
