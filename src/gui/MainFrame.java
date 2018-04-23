package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public JPanel cards;
    public GamePanel gamePanel;
    public MainFrame() {
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
