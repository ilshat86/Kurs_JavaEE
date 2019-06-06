package Part01.Lesson07.Task01;

import java.io.Serializable;

/**
 * Человек
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private int weight;
    private Sex sex;

    /**
     * Конструктор
     *
     * @param name
     * @param age
     * @param weight
     * @param sex
     */
    public Person(String name, int age, int weight, Sex sex) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.sex = sex;
    }

    public enum Sex {
        MAN,
        WOMAN
    }

    @Override
    public String toString() {
            return "Name: " + name + " Age: " + age + " Weight: " + weight + " Sex: " + sex;
        }
}

