package chess_game.pieces;

import board_game.Alliance;
import chess_game.ChessPiece;
import chess_game.enums.Direction;

import java.util.Collection;
import java.util.List;

import static chess_game.enums.Direction.*;

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
