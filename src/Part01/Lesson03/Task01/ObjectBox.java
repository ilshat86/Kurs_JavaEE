package Part01.Lesson03.Task01;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Класс объектоы
 */
public class ObjectBox {

    protected HashSet<Object> hashSet;

    /**
     * Конструктор
     */
    public ObjectBox() {
        hashSet = new HashSet<>();
    }

    /**
     * Добавляет объект в коллекцию
     *
     * @param o
     */
    public void Objectadd(Object o) {
        hashSet.add(o);

    }

    /**
     * Удаляет объект из коллекции
     *
     * @param o
     */
    public void Objectdelete(Object o) {
        hashSet.remove(o);

    }

    /**
     * Выводит на экран список элементов коллекции
     */
    public void dump() {
        for (Iterator<?> i = hashSet.iterator(); i.hasNext(); ) {
            Object o = i.next();
            System.out.print(o + " ");
        }
        System.out.println();
    }
}
