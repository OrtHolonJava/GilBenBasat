package gchess.chess;

import gchess.boardgame.Alliance;
import gchess.boardgame.Player;


public abstract class ChessPlayer extends Player<ChessGame> {
    public ChessPlayer(String name, Alliance alliance) {
        super(name, alliance);
    }
}
