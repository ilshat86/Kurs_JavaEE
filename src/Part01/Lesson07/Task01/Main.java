package Part01.Lesson07.Task01;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Основной класс
 */

public class Main {

    /**
     * Основной метод
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Марина", 20, 50, Person.Sex.WOMAN));
        personList.add(new Person("Иван", 39, 70, Person.Sex.MAN));
        personList.add(new Person("Светлана", 32, 45, Person.Sex.WOMAN));
        personList.add(new Person("Дмитрий", 45, 78, Person.Sex.MAN));

        String fileName = "lesson07";
        Serial serial = new Serial(fileName);

        /**
         * Сериализация в файл
         */
        personList.forEach(person ->
                serial.serialize(person, fileName)
        );

        ArrayList<Person> lines = new ArrayList<>();

        /**
         * Десериализация из файла
         */
        for (int i = 0; i < personList.size(); i++) {
            lines.add((Person) serial.deSerialize(fileName));
        }

        lines.forEach(System.out::println);

    }
}