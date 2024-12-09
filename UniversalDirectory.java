import java.io.File;

public class UniversalDirectory {
    public static void universalDirectory() {

        File ordner = new File("C:/StechenRechner");

        if (!ordner.exists()) {
            ordner.mkdir();
        }else{
            System.out.println("Ordner wurde bereits erstellt");
        }}}
