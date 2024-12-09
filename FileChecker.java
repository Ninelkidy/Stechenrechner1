import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileChecker {
    private JFrame messageFrame;
    public FileChecker()
    {

        boolean reset = false;
        String directoryPath = "C:/StechenRechner";

        File[] files = {
                new File(directoryPath + "/schnee.jpg"),
                new File(directoryPath + "/background.png"),
                new File(directoryPath + "/background2.jpg"),
                new File(directoryPath + "/bubbles.png"),
                new File(directoryPath + "/discord.wav"),
                new File(directoryPath + "/font5.ttf"),
                new File(directoryPath + "/imageStechen.png"),
                new File(directoryPath + "/INVASION2000.ttf"),
                new File(directoryPath + "/Modern.jpg"),
                new File(directoryPath + "/My Neck.wav"),
                new File(directoryPath + "/ringdingdong.wav"),
                new File(directoryPath + "/ueberstundenPremium.jpg")
        };

        String[] urls = {
                "https://drive.google.com/uc?export=download&id=1pLvlNibFVf4BYAQk9ocbnkOHPgm2c1Sv",
                "https://drive.google.com/uc?export=download&id=1bhyhrgEu51so89Tngvxr8unD-Q_uXdLo",
                "https://drive.google.com/uc?export=download&id=18clg-oVNP2LUO_JsizV7MRhjIN7qdXPz",
                "https://drive.google.com/uc?export=download&id=1lgdDSTyaTbs9vn-3njXIkEE6zjXOUU93",
                "https://drive.google.com/file/d/1UnDrRGfPqub__a_I4sOWYdg-qadKNLMp/view?usp=drive_link",
                "https://drive.google.com/uc?export=download&id=1n8bjFDOYv3svtzNNpZzkOJEPbmxjkUJz",
                "https://drive.google.com/uc?export=download&id=1TVshwkxDWBn1_8jqOYqN7TuyRsBPRiX8",
                "https://drive.google.com/uc?export=download&id=1JT2w5dU6k4LF-fYVldB_fiF_N91kesZk",
                "https://drive.google.com/uc?export=download&id=1cM5a9k2dBdGMnEJ6Uihcek6JuRp_O37Y",
                "https://drive.google.com/uc?export=download&id=1rrChO-NeHvC5YFGrGeYu4ZrmY0EkAUxn",
                "https://drive.google.com/uc?export=download&id=1eeP18gdltpznOiqqEWiedZcraJBhFTWJ",
                "https://drive.google.com/uc?export=download&id=1PM3P_73QmxJlvlNXDmg7IrAe-KiFQzIh"
        };


        Path saveDirectory = Path.of(directoryPath);
        try {
            if (!Files.exists(saveDirectory)) {
                Files.createDirectories(saveDirectory);
            }
        } catch (IOException e) {
            System.out.println("Failed to create directory: " + e.getMessage());
            e.printStackTrace();
            return;
        }


        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            String url = urls[i];
            if (file.exists()) {
                System.out.println(file.getName() + " is present");
            } else {
                if(!reset){
                    showMessage();
                }
                System.out.println(file.getName() + " is missing. Downloading...");
                reset = true;
                try {
                    downloadFile(url, file.toPath());
                    System.out.println(file.getName() + " downloaded successfully.");
                } catch (IOException e) {
                    System.out.println("Failed to download " + file.getName() + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        if(reset){
            System.exit(1);
        }
    }

    private static void downloadFile(String fileUrl, Path savePath) throws IOException {
        try (var inputStream = new URL(fileUrl).openStream()) {
            Files.copy(inputStream, savePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
    private void showMessage() {
        messageFrame = new JFrame("Downloading Files");
        JLabel label = new JLabel("Wait, files are downloading...", SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(16f)); // Increase font size for better visibility
        messageFrame.add(label);
        messageFrame.setSize(300, 100);
        messageFrame.setLocationRelativeTo(null); // Center the window on the screen
        messageFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Prevent closing the message
        messageFrame.setAlwaysOnTop(true); // Ensure the message is always on top
        messageFrame.setVisible(true);
    }
}
