package board_game;

import exceptions.PositionOutOfBoardException;

import java.util.HashMap;
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

    protected void setPiece(Position pos, T piece) {
        _positionsToPieces.put(pos, piece);
    }

    protected void removePiece(Position pos) {
        _positionsToPieces.remove(pos);
    }

    protected void movePiece(Position source, Position destination) {
        setPiece(destination, _positionsToPieces.get(source));
        removePiece(source);
    }

    public Move getLastMove() {
        return _moveHistory.lastElement();
    }

    public abstract boolean isOnBoard(Position pos);

    public abstract void executeMove(Move move);

    public abstract void undo();
}
