package chess_game.pieces;

import board_game.Alliance;
import chess_game.ChessPiece;
import chess_game.enums.Direction;

import java.util.Collection;
import java.util.List;

import static board_game.Alliance.WHITE;
import static chess_game.enums.Direction.*;

public class Rook extends ChessPiece {
    private final static List<Direction> MOVEMENT_DIRECTIONS = List.of(NORTH, SOUTH, EAST, WEST);
    private int _moveCount;
    public Rook(Alliance alliance) {
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
        return true;
    }

    @Override
    public String toString() {
        return (_alliance == WHITE ? "\033[1;36m\u2656" : "\033[1;30m\u2656");
    }
}
