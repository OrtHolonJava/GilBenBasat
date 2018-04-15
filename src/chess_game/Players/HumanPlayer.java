package chess_game.Players;

import board_game.Move;
import chess_game.ChessGame;
import chess_game.ChessPlayer;
import chess_game.Players.players_utils.UI;

public class HumanPlayer extends ChessPlayer {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public Move getNextMove(ChessGame game) {
        UI ui = new UI(game);
        return ui.run();
    }

    @Override
    public void onGameEnded(ChessGame game) {
        System.out.println(game.getGameState());
    }
}
