package Part01.Lesson02.Task03;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Метод №1 для сортировки - метод пузырька
 */
public class Sort1 implements Sort {

    @Override
    public void sort(ArrayList<Person> arrayList) {

        boolean isSorted = false;

        while (!isSorted) {
            isSorted = true;

            for(int i = 0; i < arrayList.size() - 1; i++){
                if (arrayList.get(i).compareTo(arrayList.get(i+1)) > 0 ) {
                    isSorted = false;

                    Collections.swap(arrayList, i, i+1);
                }
            }
        }
    }
}
