package chess_game.pieces;

import board_game.Alliance;
import chess_game.ChessPiece;
import chess_game.enums.Direction;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static board_game.Alliance.WHITE;
import static chess_game.enums.Direction.*;

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

    @Override
    public String toString() {
        return (_alliance == WHITE ? "\033[1;36m\u2659" : "\033[1;30m\u2659");
    }
}
