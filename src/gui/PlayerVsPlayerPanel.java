package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.Format;
import java.text.NumberFormat;

public class PlayerVsPlayerPanel extends JPanel implements PropertyChangeListener {
    private JTextField textField;
    private JTextField textField_1;
    private PlayerVsPlayerPanel _this;
    public PlayerVsPlayerPanel() {
        _this = this;
        setLayout(null);
        Label label = new Label("Game mode:");
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(283, 164, 107, 19);
        add(label);

        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Classic", "Atomic", "960-Chess", "King of the Hill", "Three Check"}));
        comboBox.setBounds(392, 164, 113, 20);
        add(comboBox);

        Label label_1 = new Label("Time:");
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label_1.setBounds(283, 195, 107, 19);
        add(label_1);

        textField = new JTextField();
        textField.setText("10");
        textField.setBounds(392, 194, 22, 20);
        add(textField);
        textField.setColumns(2);

        JLabel lblM = new JLabel("m");
        lblM.setBounds(416, 200, 13, 14);
        add(lblM);

        JLabel label_2 = new JLabel("+");
        label_2.setBounds(432, 195, 13, 14);
        add(label_2);

        textField_1 = new JTextField();
        textField_1.setText("3");
        textField_1.setColumns(2);
        textField_1.setBounds(448, 195, 22, 20);
        add(textField_1);

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
                JPanel cards = ((MainFrame) SwingUtilities.getWindowAncestor(_this)).cards;
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.show(cards, "2");
            }
        });
        add(btnNewButton);

        JLabel lblPlayerVsPlayer = new JLabel("Player vs Player");
        lblPlayerVsPlayer.setFont(new Font("Tahoma", Font.PLAIN, 46));
        lblPlayerVsPlayer.setBounds(230, 80, 326, 60);
        add(lblPlayerVsPlayer);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
