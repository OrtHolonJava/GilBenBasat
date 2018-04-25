package gchess;

import gchess.boardgame.Alliance;
import gchess.chess.players.UI;
import gchess.enums.GameMode;
import gchess.exceptions.GChessThrowable;
import gchess.exceptions.UnknownGameMode;

public interface GChessAPI {
    /**
     * This function starts a new game of player vs player.
     * @param mode The game mode, e.g CLASSIC, CHESS960, THREE_CHECK etc.
     * @param ui The user interface of the two players.
     * @see UI
     * @throws UnknownGameMode When an unknown game mode is sent as a parameter this exception is thrown.
     */
    void startNewPlayerVsPlayerGame(GameMode mode, UI ui) throws UnknownGameMode;

    /**
     * This function starts a new player vs ai game.
     * @param mode The game mode, e.g CLASSIC, CHESS960, THREE_CHECK etc.
     * @param playerAlliance The human player alliance (BLACK or WHITE).
     * @param aiDifficulty The difficulty of the ai.
     * @param ui The user interface of the human player.
     * @throws GChessThrowable This exception is thrown whenever the game-mode is unknown or the ai difficulty is unknown.
     */
    void startNewPlayerVsAiGame(GameMode mode, Alliance playerAlliance, int aiDifficulty, UI ui) throws GChessThrowable;
}
