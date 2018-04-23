package gui;

import gchess.boardgame.Position;
import gchess.chess.enums.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tile extends JPanel {
    public static ImageUtils imageUtils = new ImageUtils();
    private GamePanel _parent;
    private Color _backgroundColor;
    private Piece _piece;
    private int _id;
    public Tile(int id, Color backgroundColor, Piece piece, GamePanel parent) {
        _backgroundColor = backgroundColor;
        _piece = piece;
        _id = id;
        _parent = parent;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                _parent.onTileClicked(_id);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int tileLength = Math.min(_parent.getHeight(), _parent.getWidth()) / 8;
        g.drawImage(imageUtils.fromPieceToImage(_piece), 0, 0, tileLength, tileLength, _backgroundColor, this);
    }

    public Color getBackgroundColor() {
        return _backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        _backgroundColor = backgroundColor;
    }

    public Piece getPiece() {
        return _piece;
    }

    public void setPiece(Piece piece) {
        _piece = piece;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }
}
