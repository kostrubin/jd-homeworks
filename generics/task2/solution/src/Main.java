import java.util.ArrayList;

public class Main {
    public static void main(String a[]) {
        Box<String, String> sample1 = new Box<>("имя", "Нетология");
        System.out.println(sample1);

        Box<Integer, Boolean> sample2 = new Box<>(1, Boolean.TRUE);
        System.out.println(sample2);

        ArrayList<Box<String, String>> list1 = new ArrayList<>();
        list1.add(sample1);

        // Ошибка возникала из-за того, что первый объект содержит ключ и значение типа String,
        // а коллекция была создана для хранения объектов с ключом String и значением Integer

        ArrayList<Box<Integer, Boolean>> list2 = new ArrayList<>();
        list2.add(sample2);
    }
}
