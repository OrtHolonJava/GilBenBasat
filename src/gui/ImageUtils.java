package gui;

import gchess.chess.enums.Direction;
import gchess.chess.enums.Piece;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static gchess.chess.enums.Direction.*;
import static gchess.chess.enums.Direction.SOUTH_WEST;
import static gchess.chess.enums.Piece.*;

public class ImageUtils {
    private HashMap<Piece, Image> _hashMap;

    public ImageUtils() {
        _hashMap = new HashMap<>();
        try {
            BufferedImage bi = ImageIO.read(getClass().getResource("/gui/drawables/pieces.png"));
            for (Piece piece : List.of(BLACK_QUEEN, BLACK_KING, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK, BLACK_PAWN,
                WHITE_QUEEN, WHITE_KING, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK, WHITE_PAWN)) {
                _hashMap.put(piece, fromPieceToImage(bi, piece));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Image fromPieceToImage(BufferedImage bi, Piece piece) {
        if (piece == null) {
            return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        }
        if (piece == Piece.BLACK_KING) {
            return bi.getSubimage(0, 0, 64, 64);
        }
        if (piece == BLACK_QUEEN) {
            return bi.getSubimage(64, 0, 64, 64);
        }
        if (piece == Piece.BLACK_ROOK) {
            return bi.getSubimage(128, 0, 64, 64);
        }
        if (piece == Piece.BLACK_KNIGHT) {
            return bi.getSubimage(192, 0, 64, 64);
        }
        if (piece == Piece.BLACK_BISHOP) {
            return bi.getSubimage(256, 0, 64, 64);
        }
        if (piece == Piece.BLACK_PAWN) {
            return bi.getSubimage(320, 0, 64, 64);
        }
        if (piece == Piece.WHITE_KING) {
            return bi.getSubimage(0, 64, 64, 64);
        }
        if (piece == Piece.WHITE_QUEEN) {
            return bi.getSubimage(64, 64, 64, 64);
        }
        if (piece == Piece.WHITE_ROOK) {
            return bi.getSubimage(128, 64, 64, 64);
        }
        if (piece == Piece.WHITE_KNIGHT) {
            return bi.getSubimage(192, 64, 64, 64);
        }
        if (piece == Piece.WHITE_BISHOP) {
            return bi.getSubimage(256, 64, 64, 64);
        }
        if (piece == Piece.WHITE_PAWN) {
            return bi.getSubimage(320, 64, 64, 64);
        }
        return null;
    }

    public Image fromPieceToImage(Piece piece) {
        if (piece == null) {
            return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        }
        return _hashMap.get(piece);
    }
}
