package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

public class PlayerVsAiPanel extends JPanel {
    private PlayerVsAiPanel _this;
    private JSlider aiDifficulty;
    JComboBox alliance;
    private JComboBox gameMode;
    private JTextField time;
    private JTextField addTime;
    public PlayerVsAiPanel() {
        _this = this;
        setLayout(null);
        Label label = new Label("Game mode:");
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(283, 164, 107, 19);
        add(label);

        gameMode = new JComboBox();
        gameMode.setModel(new DefaultComboBoxModel(new String[] {"Classic", "Atomic", "Chess960", "King_of_the_Hill", "Three_Check"}));
        gameMode.setBounds(392, 164, 113, 20);
        add(gameMode);

        Label label_1 = new Label("Time:");
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label_1.setBounds(283, 195, 107, 19);
        add(label_1);

        time = new JTextField();
        time.setText("10");
        time.setBounds(392, 194, 22, 20);
        time.setColumns(2);
        add(time);

        JLabel m = new JLabel("m");
        m.setBounds(416, 200, 13, 14);
        add(m);

        JLabel label_2 = new JLabel("+");
        label_2.setBounds(432, 195, 13, 14);
        add(label_2);

        addTime = new JTextField();
        addTime.setText("3");
        addTime.setColumns(2);
        addTime.setBounds(448, 195, 22, 20);
        add(addTime);

        JLabel lblS = new JLabel("s");
        lblS.setBounds(472, 201, 13, 14);
        add(lblS);

        JButton btnNewButton = new JButton("Start Game");
        btnNewButton.setBounds(333, 315, 109, 23);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePrefs();
                JPanel cards = ((MainFrame) SwingUtilities.getWindowAncestor(_this)).cards;
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.show(cards, "3");
            }
        });
        add(btnNewButton);

        JLabel lblPlayerVsPlayer = new JLabel("Player vs Computer");
        lblPlayerVsPlayer.setFont(new Font("Tahoma", Font.PLAIN, 46));
        lblPlayerVsPlayer.setBounds(190, 81, 417, 60);
        add(lblPlayerVsPlayer);

        Label label_3 = new Label("Your Alliance:");
        label_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label_3.setBounds(283, 226, 107, 19);
        add(label_3);

        alliance = new JComboBox();
        alliance.setModel(new DefaultComboBoxModel(new String[] {"White", "Black"}));
        alliance.setBounds(392, 226, 113, 20);
        add(alliance);

        aiDifficulty = new JSlider();
        aiDifficulty.setSnapToTicks(true);
        aiDifficulty.setPaintTicks(true);
        aiDifficulty.setPaintLabels(true);
        aiDifficulty.setValue(5);
        aiDifficulty.setMajorTickSpacing(1);
        aiDifficulty.setMinimum(1);
        aiDifficulty.setMaximum(10);
        aiDifficulty.setBounds(392, 259, 113, 45);
        add(aiDifficulty);

        Label label_4 = new Label("AI difficulty:");
        label_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label_4.setBounds(283, 270, 107, 19);
        add(label_4);
    }

    private void savePrefs() {
        Preferences prefs = Preferences.userNodeForPackage(this.getClass());
        prefs.put("gameMode", ((String)gameMode.getSelectedItem()).toUpperCase());
        prefs.putBoolean("isWhite", ((String)alliance.getSelectedItem()).equals("White"));
        prefs.putInt("aiDifficulty", aiDifficulty.getValue());
        prefs.putBoolean("isAiGame", true);
    }
}
