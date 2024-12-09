import java.io.File;

public class FileChecker {

    public FileChecker(){
        check();
    }

    void check(){
        File schnee = new File("C:/StechenRechner/schnee.jpg");
        File background = new File("C:/StechenRechner/background.jpg");
        File background2 = new File("C:/StechenRechner/background2.jpg");
        File bubbles = new File("C:/StechenRechner/bubbles.png");
        File discord = new File("C:/StechenRechner/discord.wav");
        File font5 = new File("C:/StechenRechner/font5.ttf");
        File images = new File("C:/StechenRechner/images.jpg");
        File imageStechen = new File("C:/StechenRechner/imageStechen.png");
        File INVASION2000 = new File("C:/StechenRechner/INVASION2000.png");
        File Modern = new File("C:/StechenRechner/Modern.jpg");
        File MyNeck = new File("C:/StechenRechner/MyNeck.wav");
        File ringdingdong = new File("C:/StechenRechner/ringdingdong.wav");
        File ueberstundenPremium = new File("C:/StechenRechner/ueberstundenPremium.jpg");

        if(schnee != null && background != null && background2 != null && bubbles != null && discord != null && font5 != null && images != null && imageStechen != null && INVASION2000 != null && Modern != null && MyNeck != null && ringdingdong != null && ueberstundenPremium != null){
            System.out.println("files sind da");
        }
        else{
            System.out.println("kein file");
        }

    }
}
