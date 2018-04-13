package chess_game;

import board_game.Alliance;
import board_game.Piece;
import chess_game.enums.Direction;

import java.util.Collection;

public abstract class ChessPiece extends Piece {
    protected ChessPiece(Alliance alliance) {
        super(alliance);
    }

    public abstract Collection<Direction> getMovementDirections();

    public abstract boolean isMovementContinuous();
}
