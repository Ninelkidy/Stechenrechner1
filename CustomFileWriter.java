import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CustomFileWriter {

    public static void writeFile(ArrayList ueberstundenRueckgabe) {
        String filePath = "C:/StechenRechner/Ueberstundenabspeichern.txt";

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File parentDir = file.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                file.createNewFile();
            }

            FileWriter writer = new FileWriter(file, true);
            writer.write(ueberstundenRueckgabe.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
