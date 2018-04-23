package gchess.boardgame;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * <h1>Board</h1>
 * This class helps implementing moves of pieces in a board game.
 * <b>Note:</b> The board is not responsible for any move executions, it only saves data about the board state.
 * @param <T> This is the the piece type that is in our game.
 *
 * @author  Gil Ben Basat
 * @version 1.0
 * @since   2018-04-23
 */
public abstract class Board <T extends Piece> {
    protected HashMap<Position, T> _positionsToPieces; // Represents the board
    protected Stack<Move> _moveHistory; // Stack of all the moves we executed in the past (for the undo operation).

    protected Board() {
        _positionsToPieces = new HashMap<>();
        _moveHistory = new Stack<>();
    }

    /**
     * This function returns the piece that seats in the position received as parameter.
     * @param pos The position of the piece we want to get.
     * @return The piece that was requested.
     */
    public T getPiece(Position pos) {
        return _positionsToPieces.get(pos);
    }

    /**
     * This function sets a desired piece in the desired location.
     * @param pos The position we want the piece to be in.
     * @param piece The piece we want to put in this position.
     */
    public void setPiece(Position pos, T piece) {
        if (piece != null) {
            _positionsToPieces.put(pos, piece);
        }
    }

    /**
     * This function removes the piece in the requested position by removing it from our hash-table.
     * @param pos The position of the piece we want to remove.
     */
    public void removePiece(Position pos) {
        _positionsToPieces.remove(pos);
    }

    /**
     * This function moves a piece from one position to another.
     * @param source The current position of the piece.
     * @param destination The desired destination for the piece.
     */
    public void movePiece(Position source, Position destination) {
        setPiece(destination, _positionsToPieces.get(source));
        removePiece(source);
    }

    /**
     * This function returns the last executed move. This is for games were the last move is important.
     * @return The last executed move.
     */
    public Move getLastMove() {
        try {
            return _moveHistory.lastElement();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * This function checks if a certain position is on the board.
     * @param pos the position to be checked.
     * @return true if the position is on the board and false otherwise.
     */
    public abstract boolean isOnBoard(Position pos);

    /**
     * This function returns a copy of the board so that any modifications on the copy won't affect the current
     * board.
     * @return A copy of the board.
     */
    public abstract Board<T> getCopy();
}
