package Part01.Lesson04;

/**
 *
 * Основной метод
 */
public class Main {
    public static void main(String[] args) throws Exception {

        Person p1 = new Person("Иван", 17, true);
        Person p2 = new Person("Петр", 20, true);
        Person p3 = new Person("Марина", 34, false);
        Person p4 = new Person("Сергей", 10, true);

        Pet pet1 = new Pet(2323, "Барсик", p1, 5);
        Pet pet2 = new Pet(3434, "Шарик", p1, 4);
        Pet pet3 = new Pet(1111, "Васька", p2, 15);
        Pet pet4 = new Pet(2222, "Шарик", p3, 10);
        Pet pet5 = new Pet(2328, "Изольда", p4, 2);

        Repository rep = new Repository();
        rep.addPerson(p1);
        rep.addPerson(p2);
        rep.addPerson(p3);
        rep.addPerson(p4);

        rep.addPet(pet1);
        rep.addPet(pet2);
        rep.addPet(pet3);
        rep.addPet(pet4);
        rep.addPet(pet5);

        System.out.println(rep.searchByPetName("Шарик"));
        System.out.println(rep.searchByPetId(1111));

        System.out.println(rep.sort());

        System.out.println("Первая сортировка: ");
        System.out.println(rep.sort());

        rep.changePet(2328, new Pet(2328, "Изольда", p3, 3 ));

        System.out.println("Вторая сортировка после редактирования: ");
        System.out.println(rep.sort());
    }
}
