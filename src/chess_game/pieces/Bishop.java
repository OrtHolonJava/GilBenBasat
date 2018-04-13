package chess_game.pieces;

import board_game.Alliance;
import chess_game.ChessPiece;
import chess_game.enums.Direction;

import java.util.Collection;
import java.util.List;

import static board_game.Alliance.WHITE;
import static chess_game.enums.Direction.*;

public class Bishop extends ChessPiece {
    private final static List<Direction> MOVEMENT_DIRECTIONS = List.of(NORTH_EAST, NORTH_WEST,
            SOUTH_EAST, SOUTH_WEST);
    public Bishop(Alliance alliance) {
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

    @Override
    public String toString() {
        return (_alliance == WHITE ? "\033[1;36m\u2657" : "\033[1;30m\u2657");
    }
}
