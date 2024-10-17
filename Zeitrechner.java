import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;
import java.util.ArrayList;
import java.util.Objects;

public class Zeitrechner extends JFrame {
    private JTextField ankunftsStundenField;
    private JTextField ankunftsMinutenField;
    private JTextField bleibZeitField;
    private JTextField pausenZeitField;
    private static JTextField ueberstundenAnzahlField;
    private static JTextField ueberstundenAnzahlMinutenField;
    private static JTextField davonverwendenField;
    private static JTextField davonverwendenFieldMinuten;
    private static JTextField wannHeuteGehenFieldMinute;
    private static JTextField wannHeuteGehenField   ;
    private JLabel ergebnisLabel;
    private JLabel ueberstundenLabel;
    private JLabel titleLabelMain;
    private static JLabel ergebnisLableUeberstunden;
    private JButton ueberstundenBalanceButton, zeitDesStechensButton, berechnenButtonUeberstunden, speichern;
    private JPanel backgroundPanel, backgroundPanelStechen, ueberstundenPanel,speichernPanel ;

    TitleScreenHandler tsHandler = new TitleScreenHandler();
    ArrayList<Integer> ueberstundenRueckgabe = new ArrayList<>();


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
                drawTextWithOutline2(g, "Ueberstundenbalance", 50, 70);
            }
        };
        ueberstundenBalanceButton.setOpaque(false);
        ueberstundenBalanceButton.setContentAreaFilled(false);
        ueberstundenBalanceButton.setBorderPainted(false);
        ueberstundenBalanceButton.setForeground(new Color(223, 149, 70));
        ueberstundenBalanceButton.setBounds(160, 180, 450, 100);
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
        zeitDesStechensButton.setBounds(180, 175, 470, 200);
        zeitDesStechensButton.addActionListener(tsHandler);

        backgroundPanel.add(ueberstundenBalanceButton);
        backgroundPanel.add(zeitDesStechensButton);

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
        Font font = loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.ITALIC, 65);
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
        Font font = loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.ITALIC, 30);
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
        Font font = loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.ITALIC, 30);
        g2d.setFont(font);

        g2d.setColor(new Color(0x2D2D4C));
        g2d.drawString(text, x - 2, y - 2); // Oben links
        g2d.drawString(text, x + 2, y - 2); // Oben rechts
        g2d.drawString(text, x - 2, y + 3); // Unten links
        g2d.drawString(text, x + 2, y + 3); // Unten rechts

        g2d.setColor(textColor);
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
        if(ueberstundenInMinuten > 0) {
            ueberstundenLabel.setText("Ueberstunden: " + ueberstundenInMinuten + " Minuten");
        }else{
            ueberstundenLabel.setText("Du hast keine Ueberstunden");
        }
    }
    public ArrayList<Integer> ueberstundenBerechnen() {
        //TODO hiiiiiiiiiiiiiiiiiiier

        int gesamtUeberStunden = (Integer.parseInt(ueberstundenAnzahlField.getText()))*60; // Wie viele überstunden man hat
        int gesamtUeberMinuten = Integer.parseInt(ueberstundenAnzahlMinutenField.getText());;
        int UeberEndMinuten = gesamtUeberStunden + gesamtUeberMinuten;

        int abziehenStunden = (Integer.parseInt(davonverwendenField.getText()))*60;  //Wie viele man abziehen will
        int abziehenMinuten = Integer.parseInt(davonverwendenFieldMinuten.getText());
        int abziehenEndMinuten = abziehenStunden + abziehenMinuten;

        int uebrigeMinuten = UeberEndMinuten - abziehenEndMinuten;
        int uebrigeStundenInt = uebrigeMinuten / 60;
        double uebrigeStunden = (double) uebrigeMinuten / 60;
        double zwischenStunden = uebrigeStundenInt;
        double ueberstudenEndMinuten = (uebrigeStunden - zwischenStunden) * 60;
        int ueberstudenEndMinutenInt = (int) Math.round(ueberstudenEndMinuten);

        int arbeitAusStunden = (Integer.parseInt(wannHeuteGehenField.getText()))*60;   //Wann man laut Stechenrechner gehen darf
        int arbeitAusMinuten = Integer.parseInt(wannHeuteGehenFieldMinute.getText());
        int arbeitAusZeit = arbeitAusStunden + arbeitAusMinuten;

        int endArbeitAusZeit = arbeitAusZeit - abziehenEndMinuten;
        int arbeitAusInt = endArbeitAusZeit / 60;
        double arbeitAusStundenDouble = (double) endArbeitAusZeit / 60;
        double zwischenArbeitAusStunden = arbeitAusInt;
        double arbeitAusEndMinuten = (arbeitAusStundenDouble - zwischenArbeitAusStunden) * 60;
        int arbeitAusEndMinutenInt = (int) Math.round(arbeitAusEndMinuten);

        System.out.println(endArbeitAusZeit);
        ergebnisLableUeberstunden.setText("Feierabend ist um: " + arbeitAusInt + " Uhr " + arbeitAusEndMinutenInt);

        ueberstundenRueckgabe.set(0, uebrigeStundenInt);
        ueberstundenRueckgabe.set(1, ueberstudenEndMinutenInt);


        return ueberstundenRueckgabe;

    }
    public void ueberstundenScreen() {

        ImageIcon backgroundImageueberstunden = new ImageIcon(Objects.requireNonNull(getClass().getResource("schnee.jpg")));

        ueberstundenPanel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImageueberstunden.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        ueberstundenPanel.setSize(800, 600);
        ueberstundenPanel.setVisible(true);
        ueberstundenPanel.setLayout(null);
        setContentPane(ueberstundenPanel);

        JLabel ueberstundenAnzahlLableStunden = new JLabel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Gesammt Ueberstunden:", 50, 100, new Color(149, 135, 191));
            }
        };
        ueberstundenAnzahlLableStunden.setBounds(0,0, 500,200);
        ueberstundenAnzahlLableStunden.setVisible(true);
        ueberstundenPanel.add(ueberstundenAnzahlLableStunden);

        ueberstundenAnzahlField = new JTextField();
        ueberstundenAnzahlField.setBounds(150, 130, 150, 25);
        ueberstundenAnzahlField.setOpaque(false);
        ueberstundenAnzahlField.setFont(loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.PLAIN, 23));
        ueberstundenPanel.add(ueberstundenAnzahlField);


        ueberstundenAnzahlMinutenField = new JTextField();
        ueberstundenAnzahlMinutenField.setBounds(350, 130, 150, 25);
        ueberstundenAnzahlMinutenField.setOpaque(false);
        ueberstundenAnzahlMinutenField.setFont(loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.PLAIN, 23));
        ueberstundenPanel.add(ueberstundenAnzahlMinutenField);


        JLabel davonverwendenLable = new JLabel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Davon verwenden:", 50, 100, new Color(149, 135, 191));
            }
        };davonverwendenLable.setBounds(0,100, 500,200);
        davonverwendenLable.setVisible(true);
        ueberstundenPanel.add(davonverwendenLable);

        davonverwendenField = new JTextField();
        davonverwendenField.setBounds(150, 240, 150, 25);
        davonverwendenField.setOpaque(false);
        davonverwendenField.setFont(loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.PLAIN, 23));
        add(davonverwendenField);

        davonverwendenFieldMinuten = new JTextField();
        davonverwendenFieldMinuten.setBounds(350, 240, 150, 25);
        davonverwendenFieldMinuten.setOpaque(false);
        davonverwendenFieldMinuten.setFont(loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.PLAIN, 23));
        add(davonverwendenFieldMinuten);

        JLabel wannHeuteGehenLable = new JLabel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Normale Arbeitszeit:", 50,100, new Color(149, 135, 191));
            }
        };wannHeuteGehenLable.setBounds(0,200, 500,200);
        wannHeuteGehenLable.setVisible(true);
        ueberstundenPanel.add(wannHeuteGehenLable);

        wannHeuteGehenField = new JTextField();
        wannHeuteGehenField.setBounds(150, 330, 150, 25);
        wannHeuteGehenField.setOpaque(false);
        wannHeuteGehenField.setFont(loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.PLAIN, 23));
        add(wannHeuteGehenField);

        wannHeuteGehenFieldMinute = new JTextField();
        wannHeuteGehenFieldMinute.setBounds(350, 330, 150, 25);
        wannHeuteGehenFieldMinute.setOpaque(false);
        wannHeuteGehenFieldMinute.setFont(loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.PLAIN, 23));
        add(wannHeuteGehenFieldMinute);

        ergebnisLableUeberstunden = new JLabel();
        ergebnisLableUeberstunden.setFont(loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.PLAIN, 20));
        ergebnisLableUeberstunden.setForeground(new Color(149, 135, 191));
        ergebnisLableUeberstunden.setBounds(70, 500, 500, 50);
        ueberstundenPanel.add(ergebnisLableUeberstunden);

        JButton berechnenButtonUeberstunden = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Berechnen", 50, 100, new Color(149, 135, 191));
            }
        };
        berechnenButtonUeberstunden.setBorderPainted(false);
        berechnenButtonUeberstunden.setOpaque(false);
        berechnenButtonUeberstunden.setContentAreaFilled(false);

        berechnenButtonUeberstunden.setBounds(15, 380, 300, 150);
        berechnenButtonUeberstunden.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ueberstundenBerechnen();
            }
        });
        ueberstundenPanel.add(berechnenButtonUeberstunden);
        }

    //-------------------------------------------------------------------------------------------------------------------

    public JPanel stechenScreen() {

        ImageIcon ueberstundenImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("background2.jpg")));

        backgroundPanelStechen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ueberstundenImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanelStechen.setSize(800, 600);
        backgroundPanelStechen.setVisible(true);
        backgroundPanelStechen.setLayout(null);
        setContentPane(backgroundPanelStechen);

        JLabel ankunftsStundenLabel = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Ankunftsstunde:", 50, 100, new Color(255, 98, 50));
            }
        };ankunftsStundenLabel.setBounds(0,0, 500,200);
        ankunftsStundenLabel.setVisible(true);
        backgroundPanelStechen.add(ankunftsStundenLabel);

        ankunftsStundenField = new JTextField();
        ankunftsStundenField.setBounds(370, 83, 150, 25);
        ankunftsStundenField.setOpaque(false);
        ankunftsStundenField.setFont(loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.PLAIN, 23));
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
        backgroundPanelStechen.add(ankunftsMinutenLabel);

        ankunftsMinutenField = new JTextField();
        ankunftsMinutenField.setBounds(370, 183, 150, 25);
        ankunftsMinutenField.setOpaque(false);
        ankunftsMinutenField.setFont(loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.PLAIN, 23));
        ankunftsMinutenField.setForeground(new Color(255, 98, 50));
        add(ankunftsMinutenField);

        JLabel bleibZeitLabel = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Bleibzeit:", 50, 300, new Color(255, 98, 50));
            }
        };bleibZeitLabel.setBounds(0,0, 500,400);
        bleibZeitLabel.setVisible(true);
        backgroundPanelStechen.add(bleibZeitLabel);

        bleibZeitField = new JTextField();
        bleibZeitField.setBounds(370, 283, 150, 25);
        bleibZeitField.setOpaque(false);
        bleibZeitField.setFont(loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.PLAIN, 23));
        bleibZeitField.setForeground(new Color(255, 98, 50));
        add(bleibZeitField);

        JLabel pausenZeitLabel = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Pausenzeit:", 50, 400, new Color(255, 98, 50));
            }
        };pausenZeitLabel.setBounds(0,0, 500,500);
        pausenZeitLabel.setVisible(true);
        backgroundPanelStechen.add(pausenZeitLabel);

        pausenZeitField = new JTextField();
        pausenZeitField.setBounds(370, 383, 150, 25);
        pausenZeitField.setOpaque(false);
        pausenZeitField.setFont(loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.PLAIN, 23));
        pausenZeitField.setForeground(new Color(255, 98, 50));
        backgroundPanelStechen.add(pausenZeitField);

        JButton berechnenButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Berechnen", 50, 100, new Color(255, 98, 50));
            }
        };
        berechnenButton.setBorderPainted(false);
        berechnenButton.setOpaque(false);
        berechnenButton.setContentAreaFilled(false);

        berechnenButton.setBounds(15, 380, 460, 150);
        berechnenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                berechneFeierabendZeit();
                ueberstunden();
            }
        });

        backgroundPanelStechen.add(berechnenButton);

        ergebnisLabel = new JLabel();
        ergebnisLabel.setFont(loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.PLAIN, 20));
        ergebnisLabel.setForeground(new Color(255, 98, 50));
        ergebnisLabel.setBounds(20, 500, 300, 50);
        backgroundPanelStechen.add(ergebnisLabel);

        ueberstundenLabel = new JLabel();
        ueberstundenLabel.setFont(loadCustomFont("INVASION2000.TTF", Font.BOLD | Font.PLAIN, 20));
        ueberstundenLabel.setForeground(new Color(255, 98, 50));
        ueberstundenLabel.setBounds(370, 500, 400, 50);
        backgroundPanelStechen.add(ueberstundenLabel);

        return backgroundPanelStechen;
    }

    public void speichernButton() {

        System.out.println("Hier ist: " + ueberstundenRueckgabe.get(0));

        speichern = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "speichern", 50, 100, new Color(255, 98, 50));
            }
        };
        speichern.setBorderPainted(false);
        speichern.setOpaque(false);
        speichern.setContentAreaFilled(false);

        speichern.setBounds(300, 380, 460, 150);
        speichern.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CustomFileWriter.writeFile(ueberstundenRueckgabe);
            }
        });
        backgroundPanelStechen.add(speichern);
    }
    public class TitleScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stechenScreen();
            speichernButton();
        }
    }
}

