import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class CustomFileWriter {

    public static void writeFile(ArrayList ueberstundenRueckgabe) {

        try {
            FileWriter writer = new FileWriter("C:/Ueberstunden/Ueberstundenabspeichern.txt", true);
            writer.write(ueberstundenRueckgabe.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
