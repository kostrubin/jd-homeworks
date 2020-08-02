public class Main {
    public static void main(String a[]) {
        Box<String> sample1 = new Box<>("Нетология");
        System.out.println(sample1);

        Box<Integer> sample2 = new Box<>(1);
        // Ошибка была из-за несовместимости типов: был задан String, но в конструктор передавался Integer
        System.out.println(sample2);

        Box<Boolean> sample3 = new Box<>(Boolean.TRUE);
        System.out.println(sample3);
    }
}