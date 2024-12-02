import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;
import java.util.Objects;

public class UeberstundenScreen extends JFrame {
    public static JTextField ueberstundenAnzahlMinutenField, ueberstundenAnzahlField, davonverwendenField, wannHeuteGehenFieldMinute, davonverwendenFieldMinuten, wannHeuteGehenField;
    public static JLabel ergebnisLableUeberstunden;
     private JButton minimizeButton, weiterButton, exitButton, returnButton;
    private JPanel  backgroundPanelStechen;
    public int endStunden, endMinuten;
    StechenScreen stechenScreen = new StechenScreen();
    static Point mouseDownCompCoords;


    public void ueberstundenScreen() {

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

        ImageIcon backgroundImageueberstunden = new ImageIcon(Objects.requireNonNull(getClass().getResource("schnee.jpg")));

        JPanel ueberstundenPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImageueberstunden.getImage(), 0, 0, getWidth(), getHeight(), this);
            }};
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

        exitButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getParent() != null){ // BEIM NEUES PANEL NOCH EIN ELSE IF SCHREIBEN
                    if (getParent() == ueberstundenPanel) {
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
        ueberstundenPanel.add(exitButton);
        ueberstundenPanel.add(exitButton);

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

        minimizeButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getParent() != null){ // BEIM NEUES PANEL NOCH EIN ELSE IF SCHREIBEN
                    if (getParent() == ueberstundenPanel) {
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
        ueberstundenPanel.add(minimizeButton);
        ueberstundenPanel.add(minimizeButton);

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
            } else {
                Berechnen rechnen = new Berechnen();
                rechnen.ueberstundenBerechnen(ueberstundenAnzahlField, ueberstundenAnzahlMinutenField, davonverwendenField, davonverwendenFieldMinuten, wannHeuteGehenField, wannHeuteGehenFieldMinute, ergebnisLableUeberstunden);// Verwende das Zeitrechner-Objekt für den Methodenaufruf
            }});
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
            new Zeitrechner();
            dispose();
        });
        ueberstundenPanel.add(returnButton);
    }
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
        }}

    public class TitleScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stechenScreen.stechenScreen();
            stechenScreen.speichernButton();
        }}}