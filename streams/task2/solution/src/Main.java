import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        Collection<Person> persons = Arrays.asList(
                new Person("Jack", "Evans", 16, Sex.MAN, Education.SECONDARY),
                new Person("Connor", "Young", 23, Sex.MAN, Education.FURTHER),
                new Person("Emily", "Harris", 42, Sex.WOMEN, Education.HIGHER),
                new Person("Harry", "Wilson", 69, Sex.MAN, Education.HIGHER),
                new Person("George", "Davies", 35, Sex.MAN, Education.FURTHER),
                new Person("Samuel", "Adamson", 40, Sex.MAN, Education.HIGHER),
                new Person("John", "Brown", 44, Sex.MAN, Education.HIGHER)
        );

        System.out.println("До 18:");
        Stream<Person> childrenStream = persons.stream();
        long count = childrenStream
            .filter(v -> v.getAge() < 18)
            .count();
        System.out.println(count);

        System.out.println("\nПризывники:");
        Stream<Person> soldiersStream = persons.stream();
        soldiersStream
            .filter(v -> v.getAge() > 18 && v.getAge() < 27)
            .map(v -> v.getFamily())
            .collect(Collectors.toList())
            .forEach(System.out::println);

        System.out.println("\nРаботоспособные с высшим образованием:");
        Stream<Person> workersStream = persons.stream();
        workersStream
                .filter(v -> v.getEducation() == Education.HIGHER && isWorkableAge(v))
                .map(v -> v.getFamily())
                .sorted(Comparator.comparing(String::toString))
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    public static boolean isWorkableAge(Person person) {
        return person.getAge() > 18 &&
            ((person.getSex() == Sex.MAN && person.getAge() < 65) ||
            (person.getSex() == Sex.WOMEN && person.getAge() < 60));
    }
}
