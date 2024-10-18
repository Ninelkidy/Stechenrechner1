import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomFileWriter {

    public static void writeFile(int ueberstundenRueckgabe) {

        try {
            FileWriter writer = new FileWriter("C:/Users/Matija Jandri/Documents/ueberstunden/Ueberstunden.txt", true);
            writer.write("" + ueberstundenRueckgabe);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
