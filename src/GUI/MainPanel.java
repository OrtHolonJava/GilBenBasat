package GUI;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private Board _board;
    public MainPanel() {
        _board = new Board();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        _board.paintComponent(g);
        setSize(Board.TILE_LENGTH * 8, Board.TILE_LENGTH * 8);
    }
}
