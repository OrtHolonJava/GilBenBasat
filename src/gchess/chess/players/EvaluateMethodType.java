package gchess.chess.players;

import gchess.boardgame.Alliance;
import gchess.chess.ChessGame;

public interface EvaluateMethodType {
    public int evaluate(ChessGame game, Alliance aiAlliance);
}
