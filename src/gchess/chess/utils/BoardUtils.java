package gchess.chess.utils;

import gchess.boardgame.Alliance;
import gchess.boardgame.Position;
import gchess.chess.ChessPiece;
import gchess.chess.enums.Piece;
import gchess.chess.pieces.*;

import static gchess.boardgame.Alliance.WHITE;
import static gchess.chess.enums.Piece.*;

public class BoardUtils {
    public static final Position BLACK_KING_START_POSITION = new Position(4, 0);
    public static final Position WHITE_KING_START_POSITION = new Position(4, 7);
    public static final Position BLACK_WEST_ROOK_START_POSITION = new Position(0, 0);
    public static final Position BLACK_EAST_ROOK_START_POSITION = new Position(7, 0);
    public static final Position WHITE_WEST_ROOK_START_POSITION = new Position(0, 7);
    public static final Position WHITE_EAST_ROOK_START_POSITION = new Position(7, 7);

    public static final Position TILE_B8 = new Position(1, 0);
    public static final Position TILE_C8 = new Position(2, 0);
    public static final Position TILE_D8 = new Position(3, 0);

    public static final Position TILE_B1 = new Position(1, 7);
    public static final Position TILE_C1 = new Position(2, 7);
    public static final Position TILE_D1 = new Position(3, 7);

    public static final Position TILE_F8 = new Position(5, 0);
    public static final Position TILE_G8 = new Position(6, 0);

    public static final Position TILE_F1 = new Position(5, 7);
    public static final Position TILE_G1 = new Position(6, 7);

    public static final Position TILE_D4 = new Position(3, 4);
    public static final Position TILE_D5 = new Position(3, 3);
    public static final Position TILE_E4 = new Position(4, 4);
    public static final Position TILE_E5 = new Position(4, 3);

    public static boolean isPromotionPos(Position pos, Alliance alliance) {
        if (alliance == Alliance.WHITE && pos.getY() == 0)
            return true;
        return alliance == Alliance.BLACK && pos.getY() == 7;
    }

    public static boolean isPawnInInitialRow(Position pos, Alliance alliance) {
        if (alliance == Alliance.WHITE && pos.getY() == 6)
            return true;
        return alliance == Alliance.BLACK && pos.getY() == 1;
    }

    public static Piece pieceToEnum(ChessPiece piece) {
        if (piece instanceof Pawn) {
            return piece.getAlliance() == WHITE ? WHITE_PAWN : BLACK_PAWN;
        } else if (piece instanceof Knight) {
            return piece.getAlliance() == WHITE ? WHITE_KNIGHT : BLACK_KNIGHT;
        } else if (piece instanceof Bishop) {
            return piece.getAlliance() == WHITE ? WHITE_BISHOP : BLACK_BISHOP;
        } else if (piece instanceof Rook) {
            return piece.getAlliance() == WHITE ? WHITE_ROOK : BLACK_ROOK;
        } else if (piece instanceof Queen) {
            return piece.getAlliance() == WHITE ? WHITE_QUEEN : BLACK_QUEEN;
        } else if (piece instanceof King) {
            return piece.getAlliance() == WHITE ? WHITE_KING : BLACK_KING;
        }
        return null;
    }

}
