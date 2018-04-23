package gchess;

import gchess.boardgame.Alliance;
import gchess.chess.players.UI;
import gchess.enums.GameMode;
import gchess.exceptions.GChessThrowable;
import gchess.exceptions.UnknownGameMode;

public interface GChessAPI {
    void startNewPlayerVsPlayerGame(GameMode mode, UI ui) throws UnknownGameMode;
    void startNewPlayerVsAiGame(GameMode mode, Alliance playerAlliance, int aiDifficulty, UI ui) throws GChessThrowable;
}
