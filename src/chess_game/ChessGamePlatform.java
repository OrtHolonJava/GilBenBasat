package chess_game;

import board_game.BoardGamePlatform;

import java.util.ArrayList;

public class ChessGamePlatform extends BoardGamePlatform<ChessGame, ChessPlayer> {
    public ChessGamePlatform(ChessGame game, ArrayList<ChessPlayer> players) {
        super(players);
        _game = game;
    }
}
