import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.Random;


public class Musik {
    String filepath;

    public Musik() {
        this.filepath = filepath;
    }
    // PlayMusic methode geklaut von inder
    public void PlayMusic(String location) {
        Random random = new Random();
        int randomZahl = random.nextInt(100);

        if (randomZahl <= 4 && location != "C:/StechenRechner/discord.wav"){
            location = "C:/StechenRechner/My Neck.wav";
        }

        try {
            File musicPath = new File(location);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                // 50% volumen setzen
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(volumeControl, 1f); // float kontrolliert das volumen
                clip.start();
                System.out.println("Playing: " + filepath);
            } else {
                System.out.println("Music file nicht gefunden");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Volumen setzen
    public void setVolume(FloatControl volumeControl, float volume) {
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        float range = max - min;
        // Volumen berechnen ??`?
        float gain = (range * volume) + min;
        volumeControl.setValue(gain);
    }
}