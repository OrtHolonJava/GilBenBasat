package gchess.chess.pieces;

import gchess.boardgame.Alliance;
import gchess.chess.ChessPiece;
import gchess.chess.enums.Direction;

import java.util.Collection;
import java.util.List;

import static gchess.boardgame.Alliance.WHITE;
import static gchess.chess.enums.Direction.*;

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
}
