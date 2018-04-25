package gchess.chess.pieces;

import gchess.boardgame.Alliance;
import gchess.chess.ChessPiece;
import gchess.chess.enums.Direction;
import gchess.chess.enums.Piece;

import java.util.Collection;
import java.util.List;

import static gchess.boardgame.Alliance.WHITE;
import static gchess.chess.enums.Direction.*;

/**
 * This is a chess piece of type King.
 */
public class King extends ChessPiece {
    private final static List<Direction> MOVEMENT_DIRECTIONS = List.of(NORTH, SOUTH, EAST, WEST, NORTH_EAST, NORTH_WEST,
            SOUTH_EAST, SOUTH_WEST);
    private int _moveCount; // For castling checks.
    public King(Alliance alliance) {
        super(alliance);
        _moveCount = 0;
    }

    public int getMoveCount() {
        return _moveCount;
    }

    public void decMoveCount() {
        _moveCount--;
    } // called when undoing a king move.

    public void incMoveCount() {
        _moveCount++;
    } // called when making a king move.

    @Override
    public Collection<Direction> getMovementDirections() {
        return MOVEMENT_DIRECTIONS;
    }

    @Override
    public boolean isMovementContinuous() {
        return false;
    }
}
