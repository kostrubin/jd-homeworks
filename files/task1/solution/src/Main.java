import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final String ROOT = "C:\\Games";
        StringBuilder sb = new StringBuilder("Start logging...\n");

        // step 1
        String src = createDir(ROOT, "src", sb);
        String res = createDir(ROOT, "res", sb);
        String savegames = createDir(ROOT, "savegames", sb);
        String temp = createDir(ROOT, "temp", sb);

        // step 2
        String main = createDir(src, "main", sb);
        String test = createDir(src, "test", sb);

        // step 3
        String mainFile = createFile(main, "Main.java", sb);
        String utilsFile = createFile(main, "Utils.java", sb);

        // step 4
        String drawables = createDir(res, "drawables", sb);
        String vectors = createDir(res, "vectors", sb);
        String icons = createDir(res, "icons", sb);

        // step 5
        String tempFile = createFile(temp, "temp.txt", sb);

        writeLog(sb);
    }

    public static String createDir(String parentDir, String dirName, StringBuilder sb) {
        File file = new File(parentDir + "\\" + dirName);
        if (file.mkdir()) {
            sb.append("SUCCESS: folder " + file.getName() + " was successfully created in " + parentDir + "\n");
        } else {
            sb.append("ERROR: folder " + file.getName() + " can't be created in " + parentDir + "\n");
        }

        return parentDir + "\\" + dirName;
    }

    public static String createFile(String parentDir, String fileName, StringBuilder sb) {
        File file = new File(parentDir + "\\" + fileName);
        try {
            if (file.createNewFile()) {
                sb.append("SUCCESS: file " + file.getName() + " was successfully created in " + parentDir + "\n");
            } else {
                sb.append("ERROR: file " + file.getName() + " can't be created in " + parentDir + "\n");
            }
        } catch (IOException e) {
            sb.append("SYSTEM ERROR: " + e.getMessage() + "\n");
        }
        return parentDir + "\\" + fileName;
    }

    private static void writeLog(StringBuilder sb) {
        try (FileWriter writer = new FileWriter("C:\\Games\\temp\\temp.txt")) {
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
