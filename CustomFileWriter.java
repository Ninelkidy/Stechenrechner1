import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomFileWriter {

    public static void writeFile(ArrayList ueberstundenRueckgabe) {

        try {
            FileWriter writer = new FileWriter("C:/Users/Alina Baum/Documents/ueberstunden/Ueberstunden.txt");
            writer.write(ueberstundenRueckgabe.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
