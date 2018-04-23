package gchess.boardgame;

import gchess.boardgame.states.InGame;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <h1>Board Game Platform</h1>
 * This class is what connects the core game (BoardGame) and the players.
 * It requests players to give their next turn instead of letting them send the next turn and by doing that makes the
 * game secure against players that would make moves for other players.
 * It also shares only a copy of the game with every player so that the players cant make global changes while getting
 * data about the game state, while still letting them make any move they want (and of any player) on the copy.
 * <b>Note:</b> This class implements Runnable to be able to be started inside a thread. This is to make players
 * that connects to the game platform to be able to run gui easily.
 * @param <T> This is the game that's being played.
 * @param <U> This is the player's type that compatible with playing this game.
 *
 * @author  Gil Ben Basat
 * @version 1.0
 * @since   2018-04-23
 */
public abstract class BoardGamePlatform<T extends BoardGame, U extends Player> implements Runnable {
    protected Collection<U> _players;
    protected T _game;
    protected int _playerTurnIndex;
    public BoardGamePlatform(ArrayList<U> players) {
        _players = players;
        _playerTurnIndex = 0;
    }

    /**
     * This function run a game and asks every player in his turn for a move.
     * <b>Note:</b> This function called automatically from a thread.
     */
    public void run() {
        GameState state = _game.getGameState();

        // Game started: tell all players.
        for (U player : _players) {
            player.onGameStarted(_game.getCopy());
        }

        while (state instanceof InGame) {
            U currentPlayer = ((ArrayList<U>)_players).get(_playerTurnIndex % _players.size());
            Move nextMove = currentPlayer.getNextMove(_game.getCopy());
            if (_game.getPossibleMoves().contains(nextMove)) {
                _game.makeMove(nextMove);

                // Player made move: tell all players.
                for (U player : _players) {
                    player.onPlayerMadeMove(_game.getCopy());
                }

                _playerTurnIndex++;
                state = _game.getGameState();
            }
        }

        // Game ended: tell all players.
        for (U player : _players) {
            player.onGameEnded(_game.getCopy());
        }
    }
}
