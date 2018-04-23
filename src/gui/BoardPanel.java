package gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BoardPanel extends JPanel {
    ArrayList<Tile> _tiles;
    public BoardPanel() {
        setLayout(new GridLayout(8, 8, 0, 0));
        setSize(512,512);
        setVisible(true);
        _tiles = new ArrayList<Tile>();
    }
    public void setTiles(ArrayList<Tile> tiles) {
        _tiles = tiles;
        refresh();
    }

    public void refresh() {
        removeAll();
        for (Tile tile : _tiles) {
            add(tile);
        }
        revalidate();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(512, 512);
    }
}

