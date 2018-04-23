package gchess.chess;

import gchess.boardgame.Alliance;
import gchess.boardgame.Piece;
import gchess.chess.enums.Direction;

import java.util.Collection;

/**
 * <h1>Chess Piece</h1>
 * This class represents an abstract chess piece that we'll use in the chess game.
 *
 * @author  Gil Ben Basat
 * @version 1.0
 * @since   2018-04-23
 */
public abstract class ChessPiece extends Piece {
    protected ChessPiece(Alliance alliance) {
        super(alliance);
    }

    /**
     * This function returns the pieces movement directions.
     * @return Collection of movement directions.
     */
    public abstract Collection<Direction> getMovementDirections();

    /**
     * This function tells if the current piece movement is continuous or not.
     * @return true if movement is continuous, false otherwise.
     */
    public abstract boolean isMovementContinuous();
}
