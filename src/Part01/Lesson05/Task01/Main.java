package Part01.Lesson05.Task01;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Основной класс
 */
public class Main {

    public static void main(String[] args) throws IOException {

        String s = "";

        List<String> stringList = new ArrayList<>();
        try {
            stringList = Files.readAllLines(Paths.get("src/Part01/Lesson05/Task01/Data/Input_file.txt"));
        } catch (IOException ex) {
            System.out.println("Ошибка открытия файла: "+ex.getMessage());
        }

        for (String str: stringList) {
            s = s + str.toLowerCase().toString() + " ";
        }

        /**
         * Происходит отделение слов от небуквенных символов и выравнивание по регистру
         */
        String[] newstr = s.replaceAll("[^а-яА-Яa-zA-Z0-9 ]", "").split(" ");

        /**
         * Оставление только уникальных слов
         */
        HashSet<String> set = new HashSet<String>();
        Collections.addAll(set, newstr);

        /**
         * Сортировка слов по алфавиту
         */
        ArrayList<String> list = new ArrayList<>(set);
        Collections.sort(list);

        /**
         * Вывод слов в отдельный файл
         */
        try (DataOutputStream dataOutputStream =
                     new DataOutputStream(new FileOutputStream("src/Part01/Lesson05/Task01/Data/Output_file.txt"))) {
            for (String str: list) {
                dataOutputStream.writeUTF(str);
            }

            System.out.println("Файл записан!");
        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }
}


