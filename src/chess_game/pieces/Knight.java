package chess_game.pieces;

import board_game.Alliance;
import chess_game.ChessPiece;
import chess_game.enums.Direction;

import java.util.Collection;
import java.util.List;

import static board_game.Alliance.WHITE;
import static chess_game.enums.Direction.*;

public class Knight extends ChessPiece {
    private final static List<Direction> MOVEMENT_DIRECTIONS = List.of(KNIGHT_EEN, KNIGHT_EES, KNIGHT_NNE, KNIGHT_NNW,
            KNIGHT_SSE, KNIGHT_SSW, KNIGHT_WWN, KNIGHT_WWS);
    public Knight(Alliance alliance) {
        super(alliance);
    }

    @Override
    public Collection<Direction> getMovementDirections() {
        return MOVEMENT_DIRECTIONS;
    }

    @Override
    public boolean isMovementContinuous() {
        return false;
    }

    @Override
    public String toString() {
        return (_alliance == WHITE ? "\033[1;36m\u2658" : "\033[1;30m\u2658");
    }
}
