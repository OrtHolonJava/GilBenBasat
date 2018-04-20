package gchess.chess.players;

import gchess.boardgame.Alliance;
import gchess.boardgame.Move;
import gchess.chess.ChessGame;
import gchess.chess.ChessPlayer;

public class HumanPlayer extends ChessPlayer {
    private UI _ui;
    public HumanPlayer(String name, Alliance alliance) {
        super(name, alliance);
        _ui = new DefaultUI();
    }

    public HumanPlayer(String name, Alliance alliance, UI ui) {
        super(name, alliance);
        _ui = ui;
    }

    @Override
    public void onPlayerMadeMove(ChessGame game) {
        _ui.onPlayerMadeMove(game);
    }

    @Override
    public void onGameStarted(ChessGame game) {
        _ui.onGameStarted(game);
    }

    @Override
    public Move getNextMove(ChessGame game) {
        return _ui.getNextMove(game);
    }

    @Override
    public void onGameEnded(ChessGame game) {
        _ui.onGameEnded(game);
    }
}
