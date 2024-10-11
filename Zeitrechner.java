import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;
import java.util.Objects;

public class Zeitrechner extends JFrame {
    private JTextField ankunftsStundenField, ankunftsMinutenField, bleibZeitField, pausenZeitField;
    private JLabel ergebnisLabel, ueberstundenLabel, titleLabelMain;
    private Container con;
    private JButton ueberstundenBalanceButton, zeitDesStechensButton;
    private JPanel backgroundPanel, backgroundPanelStechen, ueberstundenPanel;



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Zeitrechner());
        new Musik();
    }

    public Zeitrechner() {
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
        ueberstundenBalanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ueberstundenScreen();
            }
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
        zeitDesStechensButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stechenScreen();
            }
        });

        backgroundPanel.add(ueberstundenBalanceButton);
        backgroundPanel.add(zeitDesStechensButton);

        // backgroundPanelStechen EINMAL
        backgroundPanelStechen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("background1.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanelStechen.setLayout(null);
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
    public void drawTextWithOutline(Graphics g, String text, int x, int y, Color textColor) {
        Graphics2D g2d = (Graphics2D) g;
        Font font = loadCustomFont("C:/Users/Alina Baum/Downloads/invasion2000//INVASION2000.TTF", Font.BOLD | Font.ITALIC, 30);
        g2d.setFont(font);

        g2d.setColor(new Color(0x2D2D4C));
        g2d.drawString(text, x - 2, y - 2); // Oben links
        g2d.drawString(text, x + 2, y - 2); // Oben rechts
        g2d.drawString(text, x - 2, y + 3); // Unten links
        g2d.drawString(text, x + 2, y + 3); // Unten rechts

        g2d.setColor(textColor);  // Benutze die übergebene Farbe
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

    //TODO WARUM ZEIGT ES DAS NICHT SONDERN IMMER NUR EINS VOM BEIDEN ICH BEENDE MEIN LEBEN
    public void stechenScreen() {
        repaint();
        revalidate();
        ueberstundenPanel.setVisible(false);
        ImageIcon backgroundImageStechen = new ImageIcon(Objects.requireNonNull(getClass().getResource("schnee.jpg")));

        backgroundPanelStechen = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImageStechen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanelStechen.setSize(800, 600);
        backgroundPanelStechen.setVisible(true);
        setContentPane(backgroundPanelStechen);


    }
    //hurensohn
    public void ueberstundenScreen() {
        repaint();
        revalidate();
        backgroundPanelStechen.setVisible(false);
        ImageIcon ueberstundenImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("background2.jpg")));

        ueberstundenPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ueberstundenImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        ueberstundenPanel.setSize(800, 600);
        ueberstundenPanel.setVisible(true);
        setContentPane(ueberstundenPanel);


        JLabel ankunftsStundenLabel = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Ankunftsstunde:", 50, 100, new Color(255, 98, 50));
            }
        };ankunftsStundenLabel.setBounds(0,0, 500,200);
        ankunftsStundenLabel.setVisible(true);
        ueberstundenPanel.add(ankunftsStundenLabel);

        ankunftsStundenField = new JTextField();
        ankunftsStundenField.setBounds(370, 83, 150, 25);
        ankunftsStundenField.setOpaque(false);
        ankunftsStundenField.setFont(loadCustomFont("C:/Users/Alina Baum/Downloads/invasion2000//INVASION2000.TTF", Font.BOLD | Font.PLAIN, 23));
        ankunftsStundenField.setForeground(new Color(255, 98, 50));
        add(ankunftsStundenField);

        JLabel ankunftsMinutenLabel = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Ankunftsminute:", 50, 200, new Color(255, 98, 50));
            }
        };ankunftsMinutenLabel.setBounds(0,0, 500,400);
        ankunftsMinutenLabel.setVisible(true);
        ueberstundenPanel.add(ankunftsMinutenLabel);

        ankunftsMinutenField = new JTextField();
        ankunftsMinutenField.setBounds(370, 183, 150, 25);
        ankunftsMinutenField.setOpaque(false);
        ankunftsMinutenField.setFont(loadCustomFont("C:/Users/Alina Baum/Downloads/invasion2000//INVASION2000.TTF", Font.BOLD | Font.PLAIN, 23));
        ankunftsMinutenField.setForeground(new Color(255, 98, 50));
        add(ankunftsMinutenField);

        JLabel bleibZeitLabel = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "bleibZeit:", 50, 300, new Color(255, 98, 50));
            }
        };bleibZeitLabel.setBounds(0,0, 500,400);
        bleibZeitLabel.setVisible(true);
        ueberstundenPanel.add(bleibZeitLabel);

    }

}