import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class StechenScreen extends JFrame {

    public JTextField bleibZeitField, pausenZeitField, ankunftsMinutenField, ankunftsStundenField;
    public static JTextField ueberstundenAnzahlField;
    public JLabel ueberstundenLabel, ergebnisLabel;
    public JButton minimizeButton,weiterButton, exitButton, returnButton;
    public JPanel  backgroundPanelStechen;
    ArrayList<Integer> ueberstundenRueckgabe = new ArrayList<>();
    ArrayList<Integer> stunden = new ArrayList<>();
    static Point mouseDownCompCoords;

    public void stechenScreen() {

        setTitle("Stechenrechner");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        this.setUndecorated(true);

        mouseDownCompCoords = null;
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseDownCompCoords = null;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point currCoords = getLocation();
                setLocation(currCoords.x + e.getX() - mouseDownCompCoords.x, currCoords.y + e.getY() - mouseDownCompCoords.y);
            }
        });

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

        minimizeButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getParent() != null){ // BEIM NEUES PANEL NOCH EIN ELSE IF SCHREIBEN
                    if (getParent() == backgroundPanelStechen) {
                        drawTextWithOutline(g, "-", 10, 20, new Color(255, 148, 69), 30);
                    } else if (getParent() == backgroundPanelStechen) {
                        drawTextWithOutline(g, "-", 10, 20, new Color(255, 98, 50), 30);
                    } else {
                        drawTextWithOutline(g, "-", 10, 20, new Color(149, 135, 191), 30);
                    }}}};
        minimizeButton.setBorderPainted(false);
        minimizeButton.setOpaque(false);
        minimizeButton.setContentAreaFilled(false);
        minimizeButton.setBounds(670, 15, 30, 35);
        minimizeButton.addActionListener(e -> {
            setState(ICONIFIED);
        });
        backgroundPanelStechen.add(minimizeButton);

        exitButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getParent() != null){ // BEIM NEUES PANEL NOCH EIN ELSE IF SCHREIBEN
                    if (getParent() == backgroundPanelStechen) {
                        drawTextWithOutline(g, "x", 10, 20, new Color(255, 148, 69), 30);
                    } else if (getParent() == backgroundPanelStechen) {
                        drawTextWithOutline(g, "x", 10, 20, new Color(255, 98, 50), 30);
                    } else {
                        drawTextWithOutline(g, "x", 10, 20, new Color(149, 135, 191), 30);
                    }}}};

        exitButton.setBorderPainted(false);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBounds(720, 13, 50, 35);
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        backgroundPanelStechen.add(exitButton);

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
                drawTextWithOutline(g, "Arbeitszeit:", 50, 300, new Color(255, 98, 50), 30);
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
            }};
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
                new Berechnen().berechneFeierabendZeit(stunden, ankunftsStundenField, ankunftsMinutenField, bleibZeitField, pausenZeitField, ergebnisLabel);
                new Berechnen().ueberstunden(stunden, bleibZeitField, ueberstundenLabel);
                //new Berechnen().ueberstundenBerechnen(ueberstundenAnzahlField);

            }});
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
            new Zeitrechner();
            dispose();
        });
        backgroundPanelStechen.add(returnButton);

        weiterButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "weiter", 10, 20, new Color(255, 98, 50), 27);
            }};
        weiterButton.setBorderPainted(true);
        weiterButton.setOpaque(false);
        weiterButton.setContentAreaFilled(false);

        weiterButton.setBounds(600, 460, 130, 35);
        weiterButton.addActionListener(e -> {
            //zeitRechner.stundenExtra();
            dispose();
            UeberstundenScreen ueberstundenScreenInstance = new UeberstundenScreen();
            ueberstundenScreenInstance.ueberstundenScreen();
            ueberstundenScreenInstance.setVisible(true);
        });
        backgroundPanelStechen.add(weiterButton);

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
            }};
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
        }}
    public void drawTextWithOutline(Graphics g, String text, int x, int y, Color textColor , int z) {
        Graphics2D g2d = (Graphics2D) g;
        Font font = loadCustomFont(Font.BOLD | Font.ITALIC, z);
        g2d.setFont(font);

        g2d.setColor(new Color(0x2D2D4C));
        g2d.drawString(text, x - 2, y - 2); // Oben links
        g2d.drawString(text, x + 2, y - 2); // Oben rechts
        g2d.drawString(text, x - 2, y + 3); // Unten links
        g2d.drawString(text, x + 2, y + 3); // Unten rechts

        g2d.setColor(textColor);
        g2d.drawString(text, x, y);
    }
    private Font loadCustomFont(int style, float size) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("INVASION2000.TTF")).deriveFont(style, size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return new Font("Serif", Font.PLAIN, 12);
        }
    }
}