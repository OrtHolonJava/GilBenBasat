package chess_game.Players;

import board_game.Alliance;
import board_game.Move;
import chess_game.ChessGame;
import chess_game.ChessPlayer;
import chess_game.UI;

public class HumanPlayer extends ChessPlayer {
    public HumanPlayer(String name, Alliance alliance) {
        super(name, alliance);
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
