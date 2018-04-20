package gchess.chess.pieces;

import gchess.boardgame.Alliance;
import gchess.chess.ChessPiece;
import gchess.chess.enums.Direction;

import java.util.Collection;
import java.util.List;

import static gchess.boardgame.Alliance.WHITE;
import static gchess.chess.enums.Direction.*;

public class Queen extends ChessPiece {
    private final static List<Direction> MOVEMENT_DIRECTIONS = List.of(NORTH, SOUTH, EAST, WEST, NORTH_EAST, NORTH_WEST,
            SOUTH_EAST, SOUTH_WEST);
    public Queen(Alliance alliance) {
        super(alliance);
    }

    @Override
    public Collection<Direction> getMovementDirections() {
        return MOVEMENT_DIRECTIONS;
    }

    @Override
    public boolean isMovementContinuous() {
        return true;
    }
}
