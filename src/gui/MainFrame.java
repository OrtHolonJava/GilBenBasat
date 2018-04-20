package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    public MainFrame() {

        // Top panel
        JPanel topPanel = new JPanel();
        topPanel.setMinimumSize(new Dimension(100, 500));

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        BufferedImage pvpIcon = null;
        try {
            pvpIcon = ImageIO.read(new File("D:\\Downloads\\player_vs_player.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JButton pvpButton = new JButton(new ImageIcon(pvpIcon));
        pvpButton.setBorder(BorderFactory.createEmptyBorder());
        pvpButton.setContentAreaFilled(false);
        pvpButton.setSize(210, 210);
        bottomPanel.add(pvpButton);

        BufferedImage pvaIcon = null;
        try {
            pvaIcon = ImageIO.read(new File("D:\\Downloads\\player_vs_pc.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JButton pvaButton = new JButton(new ImageIcon(pvaIcon));
        pvaButton.setBorder(BorderFactory.createEmptyBorder());
        pvaButton.setContentAreaFilled(false);
        pvaButton.setSize(210, 210);
        bottomPanel.add(pvaButton);

        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(40);
        bottomPanel.setLayout(flowLayout);

        // Adding panels
        topPanel.setVisible(true);
        add(topPanel);
        bottomPanel.setVisible(true);
        add(bottomPanel);

        // Set window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
    }
}
