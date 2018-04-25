package gchess.chess.pieces;

import gchess.boardgame.Alliance;
import gchess.chess.ChessPiece;
import gchess.chess.enums.Direction;

import java.util.Collection;
import java.util.List;

import static gchess.boardgame.Alliance.WHITE;
import static gchess.chess.enums.Direction.*;

/**
 * This is a chess piece of type Pawn.
 */
public class Pawn extends ChessPiece {
    public Pawn(Alliance alliance) {
        super(alliance);
    }

    @Override
    public Collection<Direction> getMovementDirections() {
        return _alliance == WHITE ? List.of(NORTH) : List.of(SOUTH);
    }

    @Override
    public boolean isMovementContinuous() {
        return false;
    }
}
