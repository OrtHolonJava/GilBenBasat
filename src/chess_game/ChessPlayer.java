package chess_game;

import board_game.Alliance;
import board_game.Player;


public abstract class ChessPlayer extends Player<ChessGame> {
    public ChessPlayer(String name) {
        super(name);
    }
}
