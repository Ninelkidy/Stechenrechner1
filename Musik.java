import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.Random;


public class Musik {
    public Musik() {
        Random random = new Random();
        int chance = random.nextInt(100);

        String filepath; // Musik file name

        if (chance < 4){
            filepath = "My Neck.wav";
        }
        else {
            filepath = "ringdingdong.wav";
        }


        PlayMusic(filepath);
    }
    // PlayMusic methode geklaut von inder
    public static void PlayMusic(String location) {
        try {
            File musicPath = new File(location);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                // 50% volumen setzen
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(volumeControl, 0.50f); // float kontrolliert das volumen
                clip.start();
                System.out.println("Playing: " + location);
            } else {
                System.out.println("Music file nicht gefunden");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Volumen setzen
    public static void setVolume(FloatControl volumeControl, float volume) {
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        float range = max - min;
        // Volumen berechnen ??`?
        float gain = (range * volume) + min;
        volumeControl.setValue(gain);
    }
}