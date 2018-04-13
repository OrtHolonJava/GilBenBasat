package board_game;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Stack;

public abstract class Board <T extends Piece> {
    protected HashMap<Position, T> _positionsToPieces; // Represents the board of size N * M
    protected Stack<Move> _moveHistory;

    protected Board() {
        _positionsToPieces = new HashMap<>();
        _moveHistory = new Stack<>();
    }

    public T getPiece(Position pos) {
        return _positionsToPieces.get(pos);
    }

    public void setPiece(Position pos, T piece) {
        _positionsToPieces.put(pos, piece);
    }

    public void removePiece(Position pos) {
        _positionsToPieces.remove(pos);
    }

    public void movePiece(Position source, Position destination) {
        setPiece(destination, _positionsToPieces.get(source));
        removePiece(source);
    }

    public Move getLastMove() {
        try {
            return _moveHistory.lastElement();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public abstract boolean isOnBoard(Position pos);

    public abstract Board<T> getCopy();
}
