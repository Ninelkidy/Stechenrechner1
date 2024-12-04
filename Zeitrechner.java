import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Point;

public class Zeitrechner extends JFrame {
    private JButton minimizeButton, weiterButton, exitButton, returnButton;
    private JPanel backgroundPanelStechen;
    static Point mouseDownCompCoords;

    public static void main(String[] args) throws InterruptedException {
        UniversalDirectory.universalDirectory();
        SwingUtilities.invokeLater(() -> new Zeitrechner());
        new Verarscher();
    }

    public Zeitrechner() {
        setTitle("Stechenrechner");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        this.setUndecorated(true);

        Musik musik = new Musik();
        musik.PlayMusic("ringdingdong.wav");

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

        JLabel titleLabelMain = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Stechenrechner", 50, 150, new Color(223, 149, 70), 65);
            }
        };
        titleLabelMain.setBounds(50, 50, 700, 200);
        backgroundPanel.add(titleLabelMain);
        setVisible(true);

        JButton ueberstundenBalanceButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Ueberstundenbalance", 50, 60, new Color(223, 149, 70), 30);
            }
        };
        ueberstundenBalanceButton.setOpaque(false);
        ueberstundenBalanceButton.setContentAreaFilled(false);
        ueberstundenBalanceButton.setBorderPainted(false);
        ueberstundenBalanceButton.setForeground(new Color(223, 149, 70));
        ueberstundenBalanceButton.setBounds(160, 230, 450, 95);
        ueberstundenBalanceButton.addActionListener(e -> {
            dispose();
            UeberstundenScreen ueberstundenScreenInstance = new UeberstundenScreen();
            ueberstundenScreenInstance.ueberstundenScreen();
            ueberstundenScreenInstance.setVisible(true);
        });

        minimizeButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "-", 10, 20, new Color(255, 148, 69), 30);
            }
        };
        minimizeButton.setBorderPainted(false);
        minimizeButton.setOpaque(false);
        minimizeButton.setContentAreaFilled(false);
        minimizeButton.setBounds(670, 15, 30, 35);
        minimizeButton.addActionListener(e -> {
            setState(ICONIFIED);
        });
        backgroundPanel.add(minimizeButton);

        exitButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "x", 10, 20, new Color(255, 148, 69), 30);
            }
        };

        exitButton.setBorderPainted(false);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBounds(720, 13, 50, 35);
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        backgroundPanel.add(exitButton);

        JButton zeitDesStechensButton = new JButton("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Zeit des Stechens", 60, 50, new Color(223, 149, 70), 30);
            }
        };
        zeitDesStechensButton.setOpaque(false);
        zeitDesStechensButton.setContentAreaFilled(false);
        zeitDesStechensButton.setBorderPainted(false);
        zeitDesStechensButton.setForeground(new Color(223, 149, 70));
        zeitDesStechensButton.setBounds(180, 325, 470, 200);
        zeitDesStechensButton.addActionListener(e -> {
            dispose();
            StechenScreen stechenScreenInstanz = new StechenScreen();
            stechenScreenInstanz.stechenScreen();
            stechenScreenInstanz.setVisible(true);
        });

        String[] options = {"Theme", "Offical", "Indie"};
        JComboBox<String> comboBox = new JComboBox<>(options) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTextWithOutline(g, "Zeit des Stechens", 50, 150, new Color(223, 149, 70), 30);
            }
        };
        comboBox.setFont(loadCustomFont(Font.BOLD | Font.PLAIN, 20));
        comboBox.setBackground(new Color(160, 215, 204));
        comboBox.setForeground(new Color(223, 149, 70));
        comboBox.setPreferredSize(new Dimension(110, 30));

        comboBox.addActionListener(e -> {
            String selectedOption = (String) comboBox.getSelectedItem();
            if ("Offical".equals(selectedOption)) {
                dispose();
                new ZeitrechnerPremium();
            } else if ("Indie".equals(selectedOption)) {
                dispose();
                new Zeitrechner();
            }
        });

        comboBox.setBorder(BorderFactory.createLineBorder(new Color(52, 28, 18), 2));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(160, 215, 204));
        panel.add(comboBox);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        getContentPane().add(panel, BorderLayout.CENTER);
        setVisible(true);
        comboBox.setBounds(15, 15, 110, 30);
        comboBox.setFocusable(false);

        backgroundPanel.add(comboBox);
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
    public void drawTextWithOutline(Graphics g, String text, int x, int y, Color textColor, int z) {
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