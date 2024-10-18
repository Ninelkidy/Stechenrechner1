import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;
import java.util.ArrayList;
import java.util.Objects;

public class ZeitrechnerPremium extends JFrame {
    private int waveOffset = 0;  // Variable für den Wellen-Offset
    private Timer waveTimer;     // Timer für die Animation

    private JTextField ankunftsStundenField;
    private JTextField ankunftsMinutenField;
    private JTextField bleibZeitField;
    private JTextField pausenZeitField;
    private static JTextField ueberstundenAnzahlField;
    private static JTextField ueberstundenAnzahlMinutenField;
    private static JTextField davonverwendenField;
    private static JTextField davonverwendenFieldMinuten;
    private static JTextField wannHeuteGehenFieldMinute;
    private static JTextField wannHeuteGehenField;
    private JLabel ergebnisLabel;
    private JLabel ueberstundenLabel;
    private static JLabel ergebnisLableUeberstunden;
    private JButton returnButton;
    private JPanel backgroundPanelStechen;

    TitleScreenHandler tsHandler = new TitleScreenHandler();
    ArrayList<Integer> ueberstundenRueckgabe = new ArrayList<>();

    public static void main(String[] args) {
        UniversalDirectory.universalDirectory();
        SwingUtilities.invokeLater(() -> new ZeitrechnerPremium());
        new Musik();
    }

    public ZeitrechnerPremium() {
        setTitle("Stechenrechner PREMIUM+");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        // TODO bild aendern
        ImageIcon backgroundImage = new ImageIcon("bubbles.png");

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        JLabel titleLabelMain = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Zeitenrechner", 50, 100, new Color(120, 120, 120), 70);
            }
        };
        titleLabelMain.setBounds(60, 100, 700, 200);
        backgroundPanel.add(titleLabelMain);
        setVisible(true);

        JButton ueberstundenBalanceButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Ueberstundenbalance", 50, 60, new Color(120, 120, 120), 37);
            }
        };
        ueberstundenBalanceButton.setOpaque(false);
        ueberstundenBalanceButton.setContentAreaFilled(false);
        ueberstundenBalanceButton.setBorderPainted(false);
        ueberstundenBalanceButton.setForeground(new Color(120, 120, 120));
        ueberstundenBalanceButton.setBounds(120, 240, 550, 75);
        ueberstundenBalanceButton.addActionListener(e -> ueberstundenScreen());

        JButton zeitDesStechensButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Feierabendzeit", 50, 150, new Color(120, 120, 120), 37);
            }
        };
        zeitDesStechensButton.setOpaque(false);
        zeitDesStechensButton.setContentAreaFilled(false);
        zeitDesStechensButton.setBorderPainted(false);
        zeitDesStechensButton.setForeground(new Color(120, 120, 120));
        zeitDesStechensButton.setBounds(130, 250, 550, 200);
        zeitDesStechensButton.addActionListener(tsHandler);

        backgroundPanel.add(ueberstundenBalanceButton);
        backgroundPanel.add(zeitDesStechensButton);

        // Timer für die Wellenbewegung starten
        waveTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                waveOffset += 10; // Wellenbewegung anpassen (kann größer oder kleiner sein)
                if (waveOffset > getWidth()) {
                    waveOffset = 0;  // Zurücksetzen, wenn es zu groß wird
                }
                repaint();  // Komponente neu zeichnen
            }
        });
        waveTimer.start();
    }

    //--------------------------------------------------------------------------------------------------------------------

    public void drawTextWithOutline(Graphics g, String text, int x, int y, Color textColor, int z) {
        Graphics2D g2d = (Graphics2D) g;
        Font font = loadCustomFont(Font.PLAIN, z);
        g2d.setFont(font);

        // Dynamischer Gradient (die Wellenbewegung erfolgt über waveOffset)
        int gradientStartX = waveOffset;
        int gradientEndX = waveOffset + 200;  // Die Länge des Verlaufs kann angepasst werden
        GradientPaint metallicPaint = new GradientPaint(gradientStartX, 0, Color.LIGHT_GRAY, gradientEndX, 50, Color.DARK_GRAY, true);

        // Text mit metallischem Verlauf zeichnen
        g2d.setPaint(metallicPaint);
        g2d.drawString(text, x, y);

     /*   // Optional: Textumrandung (Schattierung)
        g2d.setColor(new Color(0x54000000, true)); // Transparenter Schatten
        g2d.drawString(text, x - 3, y - 3); // Oben links
        g2d.drawString(text, x + 3, y - 3); // Oben rechts
        g2d.drawString(text, x - 3, y + 3); // Unten links
        g2d.drawString(text, x + 3, y + 3); // Unten rechts */
    }

    private Font loadCustomFont(int style, float size) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("font5.ttf")).deriveFont(style, size);
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
        ueberstundenRueckgabe.add(ueberstundenInMinuten);
    }
    public void ueberstundenBerechnen() {

        int gesamtUeberStunden = (Integer.parseInt(ueberstundenAnzahlField.getText()))*60; // Wie viele überstunden man hat
        int gesamtUeberMinuten = Integer.parseInt(ueberstundenAnzahlMinutenField.getText());
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

        ergebnisLableUeberstunden.setText("Feierabend ist um: " + arbeitAusInt + " Uhr " + arbeitAusEndMinutenInt);
    }
    public void ueberstundenScreen() {

        ImageIcon backgroundImageueberstunden = new ImageIcon(Objects.requireNonNull(getClass().getResource("schnee.jpg")));

        JPanel ueberstundenPanel = new JPanel() {
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
                drawTextWithOutline(g, "Gesammt Ueberstunden:", 50, 100, new Color(149, 135, 191), 30);
            }};
        ueberstundenAnzahlLableStunden.setBounds(0,0, 500,200);
        ueberstundenAnzahlLableStunden.setVisible(true);
        ueberstundenPanel.add(ueberstundenAnzahlLableStunden);

        ueberstundenAnzahlField = new JTextField();
        ueberstundenAnzahlField.setBounds(150, 130, 150, 25);
        ueberstundenAnzahlField.setOpaque(false);
        ueberstundenAnzahlField.setFont(loadCustomFont(Font.BOLD | Font.PLAIN, 23));
        ueberstundenPanel.add(ueberstundenAnzahlField);

        ueberstundenAnzahlMinutenField = new JTextField();
        ueberstundenAnzahlMinutenField.setBounds(350, 130, 150, 25);
        ueberstundenAnzahlMinutenField.setOpaque(false);
        ueberstundenAnzahlMinutenField.setFont(loadCustomFont(Font.BOLD | Font.PLAIN, 23));
        ueberstundenPanel.add(ueberstundenAnzahlMinutenField);

        JLabel davonverwendenLable = new JLabel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Davon verwenden:", 50, 100, new Color(149, 135, 191), 30);
            }};
        davonverwendenLable.setBounds(0,100, 500,200);
        davonverwendenLable.setVisible(true);
        ueberstundenPanel.add(davonverwendenLable);

        davonverwendenField = new JTextField();
        davonverwendenField.setBounds(150, 240, 150, 25);
        davonverwendenField.setOpaque(false);
        davonverwendenField.setFont(loadCustomFont(Font.BOLD | Font.PLAIN, 23));
        add(davonverwendenField);

        davonverwendenFieldMinuten = new JTextField();
        davonverwendenFieldMinuten.setBounds(350, 240, 150, 25);
        davonverwendenFieldMinuten.setOpaque(false);
        davonverwendenFieldMinuten.setFont(loadCustomFont(Font.BOLD | Font.PLAIN, 23));
        add(davonverwendenFieldMinuten);

        JLabel wannHeuteGehenLable = new JLabel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Normale Arbeitszeit:", 50,100, new Color(149, 135, 191), 30);
            }};
        wannHeuteGehenLable.setBounds(0,200, 500,200);
        wannHeuteGehenLable.setVisible(true);
        ueberstundenPanel.add(wannHeuteGehenLable);

        wannHeuteGehenField = new JTextField();
        wannHeuteGehenField.setBounds(150, 330, 150, 25);
        wannHeuteGehenField.setOpaque(false);
        wannHeuteGehenField.setFont(loadCustomFont(Font.BOLD | Font.PLAIN, 23));
        add(wannHeuteGehenField);

        wannHeuteGehenFieldMinute = new JTextField();
        wannHeuteGehenFieldMinute.setBounds(350, 330, 150, 25);
        wannHeuteGehenFieldMinute.setOpaque(false);
        wannHeuteGehenFieldMinute.setFont(loadCustomFont(Font.BOLD | Font.PLAIN, 23));
        add(wannHeuteGehenFieldMinute);

        ergebnisLableUeberstunden = new JLabel();
        ergebnisLableUeberstunden.setFont(loadCustomFont(Font.BOLD | Font.PLAIN, 20));
        ergebnisLableUeberstunden.setForeground(new Color(149, 135, 191));
        ergebnisLableUeberstunden.setBounds(70, 500, 500, 50);
        ueberstundenPanel.add(ergebnisLableUeberstunden);

        JButton berechnenButtonUeberstunden = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Berechnen", 50, 100, new Color(149, 135, 191), 30);
            }};
        berechnenButtonUeberstunden.setBorderPainted(false);
        berechnenButtonUeberstunden.setOpaque(false);
        berechnenButtonUeberstunden.setContentAreaFilled(false);

        berechnenButtonUeberstunden.setBounds(15, 380, 300, 150);
        berechnenButtonUeberstunden.addActionListener(e -> {
            if (!ueberstundenAnzahlField.isValid() || !ueberstundenAnzahlMinutenField.isValid() || !davonverwendenField.isValid() || !davonverwendenFieldMinuten.isValid() || !wannHeuteGehenField.isValid() || !wannHeuteGehenFieldMinute.isValid()) {
                System.err.println("nicht valid");
                ueberstundenScreen();
            }else{
                ueberstundenBerechnen();
            }
        });
        ueberstundenPanel.add(berechnenButtonUeberstunden);

        returnButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "<-", 10, 20, new Color(149, 135, 191), 30);
            }};
        returnButton.setBorderPainted(false);
        returnButton.setOpaque(false);
        returnButton.setContentAreaFilled(false);

        returnButton.setBounds(10, 20, 50, 35);
        returnButton.addActionListener(e -> {
            new ZeitrechnerPremium();
            dispose();
        });
        ueberstundenPanel.add(returnButton);
    }
    //-------------------------------------------------------------------------------------------------------------------
    public void stechenScreen() {
        ImageIcon ueberstundenImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("background2.jpg")));

        backgroundPanelStechen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ueberstundenImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }};
        backgroundPanelStechen.setSize(800, 600);
        backgroundPanelStechen.setVisible(true);
        backgroundPanelStechen.setLayout(null);
        setContentPane(backgroundPanelStechen);

        JLabel ankunftsStundenLabel = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Ankunftsstunde:", 50, 100, new Color(255, 98, 50), 30);
            }};
        ankunftsStundenLabel.setBounds(0,0, 500,200);
        ankunftsStundenLabel.setVisible(true);
        backgroundPanelStechen.add(ankunftsStundenLabel);

        ankunftsStundenField = new JTextField();
        ankunftsStundenField.setBounds(370, 83, 150, 25);
        ankunftsStundenField.setOpaque(false);
        ankunftsStundenField.setFont(loadCustomFont(Font.BOLD | Font.PLAIN, 23));
        ankunftsStundenField.setForeground(new Color(255, 98, 50));
        add(ankunftsStundenField);

        JLabel ankunftsMinutenLabel = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Ankunftsminute:", 50, 200, new Color(255, 98, 50), 30);
            }};
        ankunftsMinutenLabel.setBounds(0,0, 500,400);
        ankunftsMinutenLabel.setVisible(true);
        backgroundPanelStechen.add(ankunftsMinutenLabel);

        ankunftsMinutenField = new JTextField();
        ankunftsMinutenField.setBounds(370, 183, 150, 25);
        ankunftsMinutenField.setOpaque(false);
        ankunftsMinutenField.setFont(loadCustomFont(Font.BOLD | Font.PLAIN, 23));
        ankunftsMinutenField.setForeground(new Color(255, 98, 50));
        add(ankunftsMinutenField);

        JLabel bleibZeitLabel = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Bleibzeit:", 50, 300, new Color(255, 98, 50), 30);
            }};
        bleibZeitLabel.setBounds(0,0, 500,400);
        bleibZeitLabel.setVisible(true);
        backgroundPanelStechen.add(bleibZeitLabel);

        bleibZeitField = new JTextField();
        bleibZeitField.setBounds(370, 283, 150, 25);
        bleibZeitField.setOpaque(false);
        bleibZeitField.setFont(loadCustomFont(Font.BOLD | Font.PLAIN, 23));
        bleibZeitField.setForeground(new Color(255, 98, 50));
        add(bleibZeitField);

        JLabel pausenZeitLabel = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Pausenzeit:", 50, 400, new Color(255, 98, 50), 30);
            }};
        pausenZeitLabel.setBounds(0,0, 500,500);
        pausenZeitLabel.setVisible(true);
        backgroundPanelStechen.add(pausenZeitLabel);

        pausenZeitField = new JTextField();
        pausenZeitField.setBounds(370, 383, 150, 25);
        pausenZeitField.setOpaque(false);
        pausenZeitField.setFont(loadCustomFont(Font.BOLD | Font.PLAIN, 23));
        pausenZeitField.setForeground(new Color(255, 98, 50));
        backgroundPanelStechen.add(pausenZeitField);

        JButton berechnenButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Berechnen", 50, 50, new Color(255, 98, 50), 30);
            }
        };
        berechnenButton.setBorderPainted(false);
        berechnenButton.setOpaque(false);
        berechnenButton.setContentAreaFilled(false);

        berechnenButton.setBounds(15, 430, 300, 75);
        berechnenButton.addActionListener(e -> {
            if (!ankunftsStundenField.isValid() || !ankunftsMinutenField.isValid() || !bleibZeitField.isValid() || !pausenZeitField.isValid()) {
                System.err.println("nicht valid");
                stechenScreen();
                speichernButton();
            }else{

                berechneFeierabendZeit();
                ueberstunden();
            }
        });
        backgroundPanelStechen.add(berechnenButton);

        returnButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "<-", 10, 20, new Color(255, 98, 50), 30);
            }};
        returnButton.setBorderPainted(false);
        returnButton.setOpaque(false);
        returnButton.setContentAreaFilled(false);

        returnButton.setBounds(10, 20, 50, 35);
        returnButton.addActionListener(e -> {
            new ZeitrechnerPremium();
            dispose();
        });
        backgroundPanelStechen.add(returnButton);

        ergebnisLabel = new JLabel();
        ergebnisLabel.setFont(loadCustomFont(Font.BOLD | Font.PLAIN, 20));
        ergebnisLabel.setForeground(new Color(255, 98, 50));
        ergebnisLabel.setBounds(20, 500, 300, 50);
        backgroundPanelStechen.add(ergebnisLabel);

        ueberstundenLabel = new JLabel();
        ueberstundenLabel.setFont(loadCustomFont(Font.BOLD | Font.PLAIN, 20));
        ueberstundenLabel.setForeground(new Color(255, 98, 50));
        ueberstundenLabel.setBounds(370, 500, 400, 50);
        backgroundPanelStechen.add(ueberstundenLabel);
    }

    public void speichernButton() {

        JButton speichern = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "speichern", 50, 30, new Color(255, 98, 50), 30);
            }
        };
        speichern.setBorderPainted(false);
        speichern.setOpaque(false);
        speichern.setContentAreaFilled(false);

        speichern.setBounds(300, 450, 250, 50);
        speichern.addActionListener(e -> CustomFileWriter.writeFile(ueberstundenRueckgabe));backgroundPanelStechen.add(speichern);
    }
    public class TitleScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stechenScreen();
            speichernButton();
        }}}

