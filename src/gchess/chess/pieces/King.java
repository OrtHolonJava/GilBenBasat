package gchess.chess.pieces;

import gchess.boardgame.Alliance;
import gchess.chess.ChessPiece;
import gchess.chess.enums.Direction;
import gchess.chess.enums.Piece;

import java.util.Collection;
import java.util.List;

import static gchess.boardgame.Alliance.WHITE;
import static gchess.chess.enums.Direction.*;

public class King extends ChessPiece {
    private final static List<Direction> MOVEMENT_DIRECTIONS = List.of(NORTH, SOUTH, EAST, WEST, NORTH_EAST, NORTH_WEST,
            SOUTH_EAST, SOUTH_WEST);
    private int _moveCount;
    public King(Alliance alliance) {
        super(alliance);
        _moveCount = 0;
    }

    public int getMoveCount() {
        return _moveCount;
    }

    public void decMoveCount() {
        _moveCount--;
    }

    public void incMoveCount() {
        _moveCount++;
    }

    @Override
    public Collection<Direction> getMovementDirections() {
        return MOVEMENT_DIRECTIONS;
    }

    @Override
    public boolean isMovementContinuous() {
        return false;
    }
}
