package gchess.boardgame;

import gchess.boardgame.states.GameEnded;

import java.util.Collection;
import java.util.LinkedList;

public abstract class BoardGame <T extends Board> {
    protected T _board;
    protected BoardGame(T board) {
        _board = board;
    }

    public abstract Collection<Move> getPossibleMoves();

    public abstract GameState getGameState();

    public abstract void makeMove(Move move);

    public abstract void undoMove();

    public abstract BoardGame<T> getCopy();
}
