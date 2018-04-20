package gchess.chess.players;

import gchess.boardgame.Move;
import gchess.chess.ChessGame;

public interface UI {
    public Move getNextMove(ChessGame game);

    public void onGameStarted(ChessGame game);

    public void onPlayerMadeMove(ChessGame game);

    public void onGameEnded(ChessGame game);
}
