import board_game.Alliance;
import board_game.Position;
import chess_game.ChessGame;
import chess_game.ChessGamePlatform;
import chess_game.ChessPiece;
import chess_game.ChessPlayer;
import chess_game.Players.AIPlayer;
import chess_game.Players.HumanPlayer;
import chess_game.interfaces.EvaluateMethodType;
import chess_game.pieces.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        AIPlayer ai = new AIPlayer("AI", Alliance.BLACK, 4, new EvaluateMethodType() {
            @Override
            public int evaluate(ChessGame game) {
                int evaluation = 0;
                for (Position pos : game.getOccupiedPositions()) {
                    ChessPiece piece = game.getPiece(pos);
                    int sign = piece.getAlliance() == Alliance.BLACK ? 1 : -1;
                    if (piece instanceof Pawn) {
                        evaluation += sign;
                    }
                    else if (piece instanceof Knight) {
                        evaluation += 3 * sign;
                    }
                    else if (piece instanceof Bishop) {
                        evaluation += 3 * sign;
                    }
                    else if (piece instanceof Rook) {
                        evaluation += 5 * sign;
                    }
                    else if (piece instanceof Queen) {
                        evaluation += 9 * sign;
                    }
                    else if (piece instanceof King) {
                        evaluation += 100 * sign;
                    }
                }
                return evaluation;
            }

        });
        HumanPlayer human = new HumanPlayer("Player", Alliance.WHITE);
        ArrayList<ChessPlayer> players = new ArrayList<>();
        players.add(human);
        players.add(ai);
        ChessGamePlatform cgp = new ChessGamePlatform(players);
        cgp.start();
    }
}
