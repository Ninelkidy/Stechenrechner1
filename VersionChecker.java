import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionChecker {
    public boolean checkVersion(){
        String fileUrl = "https://drive.google.com/uc?export=download&id=1hrRD0qzmulf-Em-zwXOxnDHbThqrNKTG"; // Replace FILE_ID with your actual file ID

        try {

            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method
            connection.setRequestMethod("GET");

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String onlineVersion = reader.readLine();
            reader.close();

            // Check the version
            if (Zeitrechner.aktuelleVersion.equals(onlineVersion)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
