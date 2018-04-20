package gchess;

import gchess.boardgame.GameState;
import gchess.boardgame.Move;

public interface GChessListener {
    public void onGameStateChanged(GameState state);
    public Move getNextMove();
}
