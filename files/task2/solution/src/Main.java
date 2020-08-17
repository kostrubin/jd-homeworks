import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(100, 3, 10, 3.0);
        GameProgress gameProgress2 = new GameProgress(80, 5, 12, 3.5);
        GameProgress gameProgress3 = new GameProgress(95, 3, 14, 4.0);

        ArrayList<String> list = new ArrayList<>();
        list.add("C:\\Games\\savegames\\save1.dat");
        list.add("C:\\Games\\savegames\\save2.dat");
        list.add("C:\\Games\\savegames\\save3.dat");

        saveGame(list.get(0), gameProgress1);
        saveGame(list.get(1), gameProgress2);
        saveGame(list.get(2), gameProgress3);

        zipFiles("C:\\Games\\savegames\\zip.zip", list);

        for (String path : list) {
            File file = new File(path);

            if (file.delete()) {
                System.out.println("File " + file + " was deleted");
            } else {
                System.out.println("Can't delete file " + file);
            }
        }
    }

    public static void saveGame(String path, GameProgress currentState) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(currentState);
            System.out.println("File " + path + " was saved");
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }

    private static void zipFiles(String targetPath, ArrayList<String> files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(targetPath))) {
            for (String file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file);
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
