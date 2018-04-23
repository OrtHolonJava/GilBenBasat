package gchess.chess;

import gchess.boardgame.Alliance;
import gchess.boardgame.BoardGamePlatform;

import java.util.ArrayList;

/**
 * <h1>Chess Game Platform</h1>
 * This class is what connects the core game (ChessGame and its children) and the players.
 * It requests players to give their next turn instead of letting them send the next turn. By doing that the game
 * becomes secure against players that would make moves for other players.
 * It also shares only a copy of the game with every player so that the players cant make global changes while getting
 * data about the game state while still letting them make any move they want (and of any player) on the copy.
 * <b>Note:</b> This class implements Runnable to be able to be started inside a thread. This is to allow the players
 * that connects to the platform to run gui easily.
 *
 * @author  Gil Ben Basat
 * @version 1.0
 * @since   2018-04-23
 */
public class ChessGamePlatform extends BoardGamePlatform<ChessGame, ChessPlayer> {
    public ChessGamePlatform(ChessGame game, ArrayList<ChessPlayer> players) {
        super(players);
        _game = game;
        if (players.get(0).getAlliance() != Alliance.WHITE) {
                _playerTurnIndex++; // make sure white is first to play
        }
    }
}
