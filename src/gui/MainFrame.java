package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    public JPanel cards;
    public GamePanel gamePanel;
    public MainFrame() {
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(new File("src/gui/drawables/chess-game-512.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setIconImage(icon);

        cards = new JPanel();
        JPanel mainPanel = new MainPanel();

        cards.setLayout(new CardLayout());
        cards.add(mainPanel, "0");
        cards.add(new PlayerVsPlayerPanel(), "1");
        cards.add(new PlayerVsAiPanel(), "2");
        gamePanel = new GamePanel();
        cards.add(gamePanel, "3");
        add(cards);

        // Set window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
    }
}
