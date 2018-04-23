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

public class GChess implements GChessAPI {
    private static final int MIN_AI_DIFFICULTY = 1;
    private static final int MAX_AI_DIFFICULTY = 10;
    private ChessGamePlatform _chessGamePlatform;

    @Override
    public void startNewPlayerVsPlayerGame(GameMode mode, UI ui) throws UnknownGameMode {
        ArrayList<ChessPlayer> players = new ArrayList<>();
        players.add(new HumanPlayer("White", WHITE, ui));
        players.add(new HumanPlayer("Black", BLACK, ui));

        _chessGamePlatform = new ChessGamePlatform(getGameFromMode(mode), players);
        Thread gameThread = new Thread(_chessGamePlatform);
        gameThread.start();
    }

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

    private int getDepthFromDifficulty(int difficulty) throws UnknownDifficulty {
        if (difficulty > MAX_AI_DIFFICULTY || difficulty < MIN_AI_DIFFICULTY) {
            throw new UnknownDifficulty("Unknown difficulty: " + difficulty);
        }
        return (difficulty + 1) / 2;
    }
}
