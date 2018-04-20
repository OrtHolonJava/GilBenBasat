package gchess.chess;

import gchess.boardgame.Alliance;
import gchess.boardgame.Piece;
import gchess.chess.enums.Direction;

import java.util.Collection;

public abstract class ChessPiece extends Piece {
    protected ChessPiece(Alliance alliance) {
        super(alliance);
    }

    public abstract Collection<Direction> getMovementDirections();

    public abstract boolean isMovementContinuous();
}
