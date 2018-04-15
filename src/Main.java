import board_game.Alliance;
import board_game.Position;
import chess_game.ChessGame;
import chess_game.ChessGamePlatform;
import chess_game.ChessPiece;
import chess_game.ChessPlayer;
import chess_game.Players.AIPlayer;
import chess_game.Players.HumanPlayer;
import chess_game.game_mods.Atomic;
import chess_game.game_mods.Chess960;
import chess_game.game_mods.KingOfTheHill;
import chess_game.game_mods.ThreeCheck;
import chess_game.interfaces.EvaluateMethodType;
import chess_game.pieces.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<ChessPlayer> players = new ArrayList<>();
        Scanner reader = new Scanner(System.in);
        String in;
        do {
            System.out.println("Pick a game to play (1 or 2):\n1) Player vs Player\n2) Player vs AI");
            in = reader.nextLine();
        } while (!(in.equals("1") || in.equals("2")));
        if (in.equals("1")) {
            players.add(new HumanPlayer("White"));
            players.add(new HumanPlayer("Black"));
        }
        else if (in.equals("2")) {
            AIPlayer ai = new AIPlayer("AI",4, new EvaluateMethodType() {
                @Override
                public int evaluate(ChessGame game) {
                    int evaluation = 0;
                    for (Position pos : game.getOccupiedPositions()) {
                        ChessPiece piece = game.getPiece(pos);
                        int sign = piece.getAlliance() == Alliance.BLACK ? 1 : -1;
                        if (piece instanceof Pawn) {
                            evaluation += sign;
                        } else if (piece instanceof Knight) {
                            evaluation += 3 * sign;
                        } else if (piece instanceof Bishop) {
                            evaluation += 3 * sign;
                        } else if (piece instanceof Rook) {
                            evaluation += 5 * sign;
                        } else if (piece instanceof Queen) {
                            evaluation += 9 * sign;
                        } else if (piece instanceof King) {
                            evaluation += 100 * sign;
                        }
                    }
                    return evaluation;
                }

            });
            HumanPlayer human = new HumanPlayer("Player");
            do {
                System.out.println("Pick a color to play with (1 or 2):\n1) White\n2) Black");
                in = reader.nextLine();
            } while (!(in.equals("1") || in.equals("2")));
            if (in.equals("1")) {
                players.add(human);
                players.add(ai);
            }
            else if (in.equals("2")) {
                players.add(ai);
                players.add(human);
            }
        }
        do {
            System.out.println("Pick a game mod (1-5):\n1) Classic\n2) Atomic\n3) Chess960\n4) King Of The Hill\n5) Three Check");
            in = reader.nextLine();
        } while (!(in.equals("1") || in.equals("2") || in.equals("3") || in.equals("4") || in.equals("5")));

        ChessGamePlatform cgp;
        switch (in) {
            case "2":
                cgp = new ChessGamePlatform(new Atomic(), players);
                break;
            case "3":
                cgp = new ChessGamePlatform(new Chess960(), players);
                break;
            case "4":
                cgp = new ChessGamePlatform(new KingOfTheHill(), players);
                break;
            case "5":
                cgp = new ChessGamePlatform(new ThreeCheck(), players);
                break;
            default:
                cgp = new ChessGamePlatform(new ChessGame(), players);
                break;
        }
        cgp.start();
    }
}
