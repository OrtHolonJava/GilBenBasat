package gchess;

import gchess.boardgame.Alliance;
import gchess.chess.ChessGame;
import gchess.chess.ChessGamePlatform;
import gchess.chess.ChessPlayer;
import gchess.chess.gamemods.Atomic;
import gchess.chess.gamemods.Chess960;
import gchess.chess.gamemods.KingOfTheHill;
import gchess.chess.gamemods.ThreeCheck;
import gchess.chess.players.AIPlayer;
import gchess.chess.players.EvaluateMethodType;
import gchess.chess.players.HumanPlayer;
import gchess.chess.players.UI;
import gchess.chess.players.evaluators.AiEvaluatorTypeA;
import gchess.enums.GameMode;
import gchess.exceptions.GChessThrowable;
import gchess.exceptions.UnknownDifficulty;
import gchess.exceptions.UnknownGameMode;

import java.util.ArrayList;

import static gchess.boardgame.Alliance.*;
import static gchess.chess.utils.ChessGameUtils.enemyOf;

/**
 * This class implements the GChessAPI interface and its the connector between the front-end and the back-end.
 */
public class GChess implements GChessAPI {
    private static final int MIN_AI_DIFFICULTY = 1;
    private static final int MAX_AI_DIFFICULTY = 10;
    private ChessGamePlatform _chessGamePlatform;

    /**
     * This function starts a new game of player vs player.
     * @param mode The game mode, e.g CLASSIC, CHESS960, THREE_CHECK etc.
     * @param ui The user interface of the two players.
     * @see UI
     * @throws UnknownGameMode When an unknown game mode is sent as a parameter this exception is thrown.
     */
    @Override
    public void startNewPlayerVsPlayerGame(GameMode mode, UI ui) throws UnknownGameMode {
        ArrayList<ChessPlayer> players = new ArrayList<>();
        players.add(new HumanPlayer("White", WHITE, ui));
        players.add(new HumanPlayer("Black", BLACK, ui));

        _chessGamePlatform = new ChessGamePlatform(getGameFromMode(mode), players);
        Thread gameThread = new Thread(_chessGamePlatform);
        gameThread.start();
    }

    /**
     * This function starts a new player vs ai game.
     * @param mode The game mode, e.g CLASSIC, CHESS960, THREE_CHECK etc.
     * @param playerAlliance The human player alliance (BLACK or WHITE).
     * @param aiDifficulty The difficulty of the ai.
     * @param ui The user interface of the human player.
     * @throws GChessThrowable This exception is thrown whenever the game-mode is unknown or the ai difficulty is unknown.
     */
    @Override
    public void startNewPlayerVsAiGame(GameMode mode, Alliance playerAlliance, int aiDifficulty, UI ui) throws GChessThrowable {
        ArrayList<ChessPlayer> players = new ArrayList<>();
        players.add(new HumanPlayer("Player", playerAlliance, ui));
        players.add(new AIPlayer("AI", enemyOf(playerAlliance), getDepthFromDifficulty(aiDifficulty),
            getEvaluatorFromDifficulty(aiDifficulty)));

        _chessGamePlatform = new ChessGamePlatform(getGameFromMode(mode), players);
        Thread gameThread = new Thread(_chessGamePlatform);
        gameThread.start();
    }

    /**
     * This function returns an instance of a game that suit the game-mode.
     * @param mode The game-mode of the game.
     * @return New instance of the suitable game.
     * @throws UnknownGameMode Thrown when an unknown game-mode is given.
     */
    private ChessGame getGameFromMode(GameMode mode) throws UnknownGameMode {
        switch (mode) {
            case CLASSIC:
                return new ChessGame();
            case ATOMIC:
                return new Atomic();
            case CHESS960:
                return new Chess960();
            case KING_OF_THE_HILL:
                return new KingOfTheHill();
            case THREE_CHECK:
                return new ThreeCheck();
            default:
                throw new UnknownGameMode("Unknown game mode: " + mode);
        }
    }

    /**
     * This function returns an ai evaluation algorithm (used in the minimax) based on the ai difficulty.
     * @param difficulty This is the difficulty of the ai.
     * @see AIPlayer
     * @return An ai evaluator.
     * @throws UnknownDifficulty When an unknown difficulty is given this exception is thrown.
     */
    private EvaluateMethodType getEvaluatorFromDifficulty(int difficulty) throws UnknownDifficulty {
        if (difficulty > MAX_AI_DIFFICULTY || difficulty < MIN_AI_DIFFICULTY) {
            throw new UnknownDifficulty("Unknown difficulty: " + difficulty);
        }
        switch (difficulty / 2) {
            case 0:
                return new AiEvaluatorTypeA();
            case 1:
                return new AiEvaluatorTypeA();
            case 2:
                return new AiEvaluatorTypeA();
            case 3:
                return new AiEvaluatorTypeA();
            case 4:
                return new AiEvaluatorTypeA();
            case 5:
                return new AiEvaluatorTypeA();
        }
        return null;
    }

    /**
     * This function returns the depth of the minimax algorithm based on the ai difficulty.
     * @param difficulty This is the difficulty of the ai.
     * @see AIPlayer
     * @return The depth of the minimax algorithm.
     * @throws UnknownDifficulty When an unknown difficulty is given this exception is thrown.
     */
    private int getDepthFromDifficulty(int difficulty) throws UnknownDifficulty {
        if (difficulty > MAX_AI_DIFFICULTY || difficulty < MIN_AI_DIFFICULTY) {
            throw new UnknownDifficulty("Unknown difficulty: " + difficulty);
        }
        return (difficulty + 1) / 2;
    }
}
