import java.util.Random;

public class Verarscher {

    public Verarscher() throws InterruptedException {
        Musik discord = new Musik();
        Random random = new Random();
        while(true){
            Thread.sleep(random.nextInt(25000, 120000));
            discord.PlayMusic("discord.wav");
        }
    }
}
