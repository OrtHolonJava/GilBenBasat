package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Gil on 30/10/2017.
 */
public class GamePanel extends JPanel {
    public GamePanel() {
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(300, 10));
        add(panel, BorderLayout.EAST);
        panel.setLayout(new BorderLayout(0, 0));

        JPanel panel_3 = new JPanel();
        panel.add(panel_3, BorderLayout.NORTH);
        panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.PAGE_AXIS));

        JLabel label = new JLabel("Black");
        panel_3.add(label);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setFont(new Font("Tahoma", Font.PLAIN, 26));

        JLabel label_1 = new JLabel("10:00 +3s");
        panel_3.add(label_1);
        label_1.setVerticalAlignment(SwingConstants.BOTTOM);
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 18));

        JPanel panel_4 = new JPanel();
        panel.add(panel_4, BorderLayout.SOUTH);
        panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.PAGE_AXIS));

        JLabel lblBlack = new JLabel("White");
        panel_4.add(lblBlack);
        lblBlack.setFont(new Font("Tahoma", Font.PLAIN, 26));

        JLabel lbls = new JLabel("10:00 +3s");
        panel_4.add(lbls);
        lbls.setHorizontalAlignment(SwingConstants.CENTER);
        lbls.setFont(new Font("Tahoma", Font.PLAIN, 18));

        JPanel panel_1 = new JPanel();
        add(panel_1, BorderLayout.SOUTH);

        JButton btnSurrender = new JButton("Surrender");
        btnSurrender.setHorizontalAlignment(SwingConstants.LEFT);
        panel_1.add(btnSurrender);

        JButton btnOfferADraw = new JButton("Offer a Draw");
        panel_1.add(btnOfferADraw);

        BoardPanel mp = new BoardPanel();
        mp.setSize(getWidth(), getHeight());
        mp.repaint();
        add(mp, BorderLayout.CENTER);
    }
        public static void main(String[] args)
        {
            JFrame frame = new JFrame("Classical Chess");
            BoardPanel mp = new BoardPanel();
            mp.setSize(frame.getWidth(), frame.getHeight());
            mp.repaint();
            frame.add(mp);
            //********
            //********
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);
            frame.setVisible(true);
        }
}
