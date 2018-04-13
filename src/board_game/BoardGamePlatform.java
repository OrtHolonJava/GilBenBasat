package board_game;

import board_game.game_states.InGame;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BoardGamePlatform<T extends BoardGame, U extends Player>{
    private Collection<U> _players;
    protected T _game;
    private int _playerTurnIndex;
    public BoardGamePlatform(ArrayList<U> players) {
        _players = players;
        _playerTurnIndex = 0;
    }

    public void start() {
        GameState state = _game.getGameState();
        while (state instanceof InGame) {
            U currentPlayer = ((ArrayList<U>)_players).get(_playerTurnIndex % _players.size());
            Move nextMove = currentPlayer.getNextMove(_game.getCopy());
            _game.makeMove(nextMove);
            _playerTurnIndex++;
            state = _game.getGameState();
        }
        for (U player : _players) {
            player.onGameEnded(_game.getCopy());
        }
    }

}
