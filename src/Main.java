import gchess.chess.ChessGame;
import gchess.chess.ChessGamePlatform;
import gchess.chess.ChessPlayer;
import gchess.chess.players.AIPlayer;
import gchess.chess.players.HumanPlayer;
import gchess.chess.players.evaluators.AiEvaluatorTypeA;
import gchess.chess.gamemods.Atomic;
import gchess.chess.gamemods.Chess960;
import gchess.chess.gamemods.KingOfTheHill;
import gchess.chess.gamemods.ThreeCheck;
import gui.MainFrame;

import java.util.ArrayList;
import java.util.Scanner;

import static gchess.boardgame.Alliance.BLACK;
import static gchess.boardgame.Alliance.WHITE;

public class Main {
    public static void main(String[] args) {
        activateDefaultGui();
    }
    private static void activateDefaultGui() {
        MainFrame mainFrame = new MainFrame();
    }
    private static void activateDefaultUi() {
        ArrayList<ChessPlayer> players = new ArrayList<>();
        Scanner reader = new Scanner(System.in);
        String in;
        do {
            System.out.println("Pick a game to play (1 or 2):\n1) Player vs Player\n2) Player vs AI");
            in = reader.nextLine();
        } while (!(in.equals("1") || in.equals("2")));
        if (in.equals("1")) {
            players.add(new HumanPlayer("White", WHITE));
            players.add(new HumanPlayer("Black", BLACK));
        }
        else if (in.equals("2")) {
            do {
                System.out.println("Pick a color to play with (1 or 2):\n1) White\n2) Black");
                in = reader.nextLine();
            } while (!(in.equals("1") || in.equals("2")));
            if (in.equals("1")) {
                players.add(new HumanPlayer("Player", WHITE));
                players.add(new AIPlayer("AI", BLACK, 4, new AiEvaluatorTypeA()));
            }
            else if (in.equals("2")) {
                players.add(new AIPlayer("AI", WHITE, 4, new AiEvaluatorTypeA()));
                players.add(new HumanPlayer("Player", BLACK));
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
        new Thread(cgp).start();
    }
}
