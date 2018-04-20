package gchess.chess;

import gchess.boardgame.Alliance;
import gchess.boardgame.BoardGamePlatform;

import java.util.ArrayList;

public class ChessGamePlatform extends BoardGamePlatform<ChessGame, ChessPlayer> {
    public ChessGamePlatform(ChessGame game, ArrayList<ChessPlayer> players) {
        super(players);
        _game = game;
        if (players.get(0).getAlliance() != Alliance.WHITE) {
                _playerTurnIndex++; // make sure white is first to play
        }
    }
}
