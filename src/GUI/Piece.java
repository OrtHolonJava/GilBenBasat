package GUI;

import java.awt.*;

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;

public enum Piece {
    WhiteQueen(WHITE), WhiteKing(WHITE), WhitePawn(WHITE), WhiteKnight(WHITE), WhiteRook(WHITE), WhiteBishop(WHITE),
    BlackQueen(BLACK), BlackKing(BLACK), BlackPawn(BLACK), BlackKnight(BLACK), BlackRook(BLACK), BlackBishop(BLACK),
    Empty(null);
    private final Color _color;
    Piece(Color color) { _color = color; }
    public Color getColor() { return _color; }
}
