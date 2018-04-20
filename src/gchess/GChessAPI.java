package gchess;

import gchess.boardgame.Alliance;
import gchess.boardgame.Move;
import gchess.boardgame.Position;
import gchess.boardgame.states.GameEnded;
import gchess.chess.players.UI;
import gchess.enums.GameMode;
import gchess.chess.enums.Piece;
import gchess.exceptions.GChessThrowable;
import gchess.exceptions.NoGameWasStarted;
import gchess.exceptions.UnknownGameMode;

import java.util.Collection;

public interface GChessAPI {
    public void startNewPlayerVsPlayerGame(GameMode mode, UI ui) throws UnknownGameMode;
    public void startNewPlayerVsAiGame(GameMode mode, Alliance playerAlliance, int aiDifficulty, UI ui) throws GChessThrowable;
    public void endGame();
}
