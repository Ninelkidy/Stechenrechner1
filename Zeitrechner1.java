import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;

public class Zeitrechner1 extends JFrame {
    private JTextField ankunftsStundenField, ankunftsMinutenField, bleibZeitField, pausenZeitField;
    private JLabel ergebnisLabel, ueberstundenLabel, titleLabelMain;
    private Container con;
    private JButton ueberstundenBalanceButton, zeitDesStechensButton;
    private JPanel backgroundPanel, backgroundPanelStechen;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Zeitrechner1());
        new Musik();
    }



    public Zeitrechner1() {
        setTitle("Stechenrechner");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon("background.png");

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);


        titleLabelMain = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Stechenrechner", 50, 100);
            }
        };
        titleLabelMain.setBounds(50, 50, 700, 200);
        backgroundPanel.add(titleLabelMain);
        setVisible(true);

        ueberstundenBalanceButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline2(g, "Ueberstundenbalance", 50, 100);
            }
        };
        ueberstundenBalanceButton.setOpaque(false);
        ueberstundenBalanceButton.setContentAreaFilled(false);
        ueberstundenBalanceButton.setBorderPainted(false);

        ueberstundenBalanceButton.setForeground(new Color(223, 149, 70));
        ueberstundenBalanceButton.setBounds(160, 170, 460, 300);
        ueberstundenBalanceButton.addActionListener(e -> {

            ueberstundenBalanceScreen();
        });


        zeitDesStechensButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline2(g, "Zeit des Stechens", 50, 150);
            }
        };
        zeitDesStechensButton.setOpaque(false);
        zeitDesStechensButton.setContentAreaFilled(false);
        zeitDesStechensButton.setBorderPainted(false);

        zeitDesStechensButton.setForeground(new Color(223, 149, 70));
        zeitDesStechensButton.setBounds(180, 190, 490, 300);
        zeitDesStechensButton.addActionListener(e -> {

            ueberstundenBalanceScreen();
        });
        backgroundPanel.add(ueberstundenBalanceButton);
        backgroundPanel.add(zeitDesStechensButton);
    }

    //--------------------------------------------------------------------------------------------------------------------

    public void drawTextWithOutline(Graphics g, String text, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;

        Font font = loadCustomFont("C:/Users/Alina Baum/Downloads/invasion2000//INVASION2000.TTF", Font.BOLD | Font.ITALIC, 65);
        g2d.setFont(font);


        g2d.setColor(new Color(0x2D2D4C));
        g2d.drawString(text, x - 2, y - 2); // Oben links
        g2d.drawString(text, x + 2, y - 2); // Oben rechts
        g2d.drawString(text, x - 2, y + 3); // Unten links
        g2d.drawString(text, x + 2, y + 3); // Unten rechts
        g2d.setColor(new Color(223, 149, 70));
        g2d.drawString(text, x, y);
    }

    public void drawTextWithOutline2(Graphics g, String text, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;


        Font font = loadCustomFont("C:/Users/Alina Baum/Downloads/invasion2000//INVASION2000.TTF", Font.BOLD | Font.ITALIC, 30);
        g2d.setFont(font);


        g2d.setColor(new Color(0x2D2D4C));
        g2d.drawString(text, x - 2, y - 2); // Oben links
        g2d.drawString(text, x + 2, y - 2); // Oben rechts
        g2d.drawString(text, x - 2, y + 3); // Unten links
        g2d.drawString(text, x + 2, y + 3); // Unten rechts


        g2d.setColor(new Color(223, 149, 70));
        g2d.drawString(text, x, y);
    }

    private Font loadCustomFont(String fontPath, int style, float size) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(style, size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return new Font("Serif", Font.PLAIN, 12);
        }
    }

    public void berechneFeierabendZeit() {
        int ankunftsStunden = Integer.parseInt(ankunftsStundenField.getText());
        int ankunftsMinuten = Integer.parseInt(ankunftsMinutenField.getText());
        double bleibZeit = Double.parseDouble(bleibZeitField.getText());
        double pausenZeit = Double.parseDouble(pausenZeitField.getText());

        double gesamtZeit = bleibZeit + pausenZeit;
        int gesamtStunden = (int) gesamtZeit;
        int gesamtMinuten = (int) ((gesamtZeit - gesamtStunden) * 60);

        int endMinuten = ankunftsMinuten + gesamtMinuten;
        int extraStunden = endMinuten / 60;
        endMinuten = endMinuten % 60;

        int endStunden = ankunftsStunden + gesamtStunden + extraStunden;

        ergebnisLabel.setText("Feierabend um " + endStunden + ":" + (endMinuten < 10 ? "0" + endMinuten : endMinuten) + " Uhr.");
    }

    public void ueberstunden() {
        double vorgegebeneArbeitszeit = 7.6 * 60;
        double gesamteArbeitszeit = Double.parseDouble(bleibZeitField.getText());
        int arbeitszeitInMinuten = (int) (gesamteArbeitszeit * 60);
        int ueberstundenInMinuten = (int) (arbeitszeitInMinuten - vorgegebeneArbeitszeit);

        if (ueberstundenInMinuten > 0) {
            ueberstundenLabel.setText("Ueberstunden: " + ueberstundenInMinuten + " Minuten");
        } else {
            ueberstundenLabel.setText("Du hast keine Ueberstunden");
        }
    }

    public void ueberstundenBalanceScreen() {
        //TODO
        backgroundPanel.setVisible(false);
        if(zeitDesStechensButton.isSelected()){

            ImageIcon backgroundImage = new ImageIcon("background.png");

            JPanel backgroundPanelStechen = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            backgroundPanelStechen.setLayout(null);
            setContentPane(backgroundPanelStechen);
        }else{

        }

    }

}