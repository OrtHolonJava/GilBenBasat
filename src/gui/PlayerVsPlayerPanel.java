package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.prefs.Preferences;

public class PlayerVsPlayerPanel extends JPanel implements PropertyChangeListener {
    private JTextField time;
    private JTextField addTime;
    private JComboBox gameMode;
    private PlayerVsPlayerPanel _this;
    public PlayerVsPlayerPanel() {
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
        add(time);
        time.setColumns(2);

        JLabel lblM = new JLabel("m");
        lblM.setBounds(416, 200, 13, 14);
        add(lblM);

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

        Checkbox checkbox = new Checkbox("Flip board (after every move)");
        checkbox.setState(true);
        checkbox.setBounds(286, 220, 172, 19);
        add(checkbox);

        JButton btnNewButton = new JButton("Start Game");
        btnNewButton.setBounds(332, 272, 109, 23);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePrefs();
                JPanel cards = ((MainFrame) SwingUtilities.getWindowAncestor(_this)).cards;
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.show(cards, "3");
                ((MainFrame) SwingUtilities.getWindowAncestor(_this)).gamePanel.start();
            }
        });
        add(btnNewButton);

        JLabel lblPlayerVsPlayer = new JLabel("Player vs Player");
        lblPlayerVsPlayer.setFont(new Font("Tahoma", Font.PLAIN, 46));
        lblPlayerVsPlayer.setBounds(230, 80, 326, 60);
        add(lblPlayerVsPlayer);
    }

    private void savePrefs() {
        Preferences prefs = Preferences.userNodeForPackage(this.getClass());
        prefs.put("gameMode", ((String)gameMode.getSelectedItem()).toUpperCase());
        prefs.putBoolean("isAiGame", false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
