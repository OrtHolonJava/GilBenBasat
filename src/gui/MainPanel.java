package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainPanel extends JPanel {
    private MainPanel _this;
    public MainPanel() {

        _this = this;

        // Top panel
        JPanel topPanel = new JPanel();
        topPanel.setMinimumSize(new Dimension(100, 500));

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        BufferedImage pvpIcon = null;
        try {
            pvpIcon = ImageIO.read(getClass().getResource("/gui/drawables/player_vs_player.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JButton pvpButton = new JButton(new ImageIcon(pvpIcon));
        pvpButton.setBorder(BorderFactory.createEmptyBorder());
        pvpButton.setContentAreaFilled(false);
        pvpButton.setSize(210, 210);
        pvpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel cards = ((MainFrame) SwingUtilities.getWindowAncestor(_this)).cards;
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.show(cards, "1");
            }
        });
        bottomPanel.add(pvpButton);

        BufferedImage pvaIcon = null;
        try {
            pvaIcon = ImageIO.read(getClass().getResource("/gui/drawables/player_vs_pc.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JButton pvaButton = new JButton(new ImageIcon(pvaIcon));
        pvaButton.setBorder(BorderFactory.createEmptyBorder());
        pvaButton.setContentAreaFilled(false);
        pvaButton.setSize(210, 210);
        pvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel cards = ((MainFrame) SwingUtilities.getWindowAncestor(_this)).cards;
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.show(cards, "2");
            }
        });
        bottomPanel.add(pvaButton);

        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(40);
        bottomPanel.setLayout(flowLayout);

        // Adding panels
        add(topPanel);
        add(bottomPanel);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
}
