import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {

        /*
            Результат выполнения, если вдруг будет лень выполнять:)
            
            Сравнение при 100:
            Один поток: 0.0167505 сек.
            Несколько:  0.0299391 сек.
            Один поток эффективнее на 44%
            ===================
            Сравнение при 1000:
            Один поток: 0.0033232 сек.
            Несколько:  0.0028585 сек.
            Несколько потоков эффективнее на 14%
            ===================
            Сравнение при 10000:
            Один поток: 0.0136276 сек.
            Несколько:  0.012452 сек.
            Несколько потоков эффективнее на 9%
            ===================
            Сравнение при 100000:
            Один поток: 0.0715574 сек.
            Несколько:  0.0311622 сек.
            Несколько потоков эффективнее на 56%
            ===================
            Сравнение при 1000000:
            Один поток: 0.2017791 сек.
            Несколько:  0.1275953 сек.
            Несколько потоков эффективнее на 37%
            ===================
            Сравнение при 10000000:
            Один поток: 1.0169252 сек.
            Несколько:  0.8047378 сек.
            Несколько потоков эффективнее на 21%
            ===================
         */

        int[] allPeopleNumber = {
            100,
            1_000,
            10_000,
            100_000,
            1_000_000,
            10_000_000
        };

        for (int currentPeopleNumber : allPeopleNumber) {
            System.out.println("Сравнение при " + currentPeopleNumber + ":");
            Collection<Person> people = getRandomPeopleList(currentPeopleNumber);
            countEfficiency(calculate(people), calculateParallel(people));
            System.out.println("===================");
        }
    }

    private static Collection<Person> getRandomPeopleList(int peopleNumber) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> people = new ArrayList<>();

        for (int i = 0; i < peopleNumber; i++) {
            people.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        return people;
    }

    private static void countEfficiency(double oneStream, double someStreams) {
        double efficiency;
        if (oneStream < someStreams) {
            efficiency = 100 - oneStream / someStreams * 100;
            System.out.printf("Один поток эффективнее на %.0f%% %n", efficiency);
        } else if (oneStream > someStreams) {
            efficiency = 100 - someStreams / oneStream * 100;
            System.out.printf("Несколько потоков эффективнее на %.0f%% %n", efficiency);
        } else {
            System.out.println("Эффективность одинакова");
        }
    }

    public static double calculate(Collection<Person> persons) {
        long startTime = System.nanoTime();

        Stream<Person> childrenStream = persons.stream();
        childrenStream
                .filter(v -> v.getAge() < 18)
                .count();

        Stream<Person> soldiersStream = persons.stream();
        soldiersStream
                .filter(v -> v.getAge() > 18 && v.getAge() < 27)
                .map(v -> v.getFamily())
                .collect(Collectors.toList());

        Stream<Person> workersStream = persons.stream();
        workersStream
                .filter(v -> v.getEducation() == Education.HIGHER && isWorkableAge(v))
                .map(v -> v.getFamily())
                .sorted(Comparator.comparing(String::toString))
                .collect(Collectors.toList());

        long stopTime = System.nanoTime();
        double processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Один поток: " + processTime + " сек.");

        return processTime;
    }

    public static double calculateParallel(Collection<Person> persons) {
        long startTime = System.nanoTime();

        Stream<Person> childrenStream = persons.parallelStream();
        childrenStream
                .filter(v -> v.getAge() < 18)
                .count();

        Stream<Person> soldiersStream = persons.parallelStream();
        soldiersStream
                .filter(v -> v.getAge() > 18 && v.getAge() < 27)
                .map(v -> v.getFamily())
                .collect(Collectors.toList());

        Stream<Person> workersStream = persons.parallelStream();
        workersStream
                .filter(v -> v.getEducation() == Education.HIGHER && isWorkableAge(v))
                .map(v -> v.getFamily())
                .sorted(Comparator.comparing(String::toString))
                .collect(Collectors.toList());

        long stopTime = System.nanoTime();
        double processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Несколько:  " + processTime + " сек.");

        return processTime;
    }

    public static boolean isWorkableAge(Person person) {
        return person.getAge() > 18 &&
            ((person.getSex() == Sex.MAN && person.getAge() < 65) ||
            (person.getSex() == Sex.WOMEN && person.getAge() < 60));
    }
}
