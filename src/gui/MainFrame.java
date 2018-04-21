package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    public JPanel cards;
    public MainFrame() {
        cards = new JPanel();
        JPanel mainPanel = new MainPanel();

        cards.setLayout(new CardLayout());
        cards.add(mainPanel, "0");
        cards.add(new PlayerVsPlayerPanel(), "1");
        cards.add(new GamePanel(), "2");

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
