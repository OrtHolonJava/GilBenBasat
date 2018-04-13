import board_game.GameState;
import board_game.Move;
import board_game.Position;
import chess_game.ChessBoard;
import chess_game.ChessGame;
import chess_game.game_states.CheckMate;
import chess_game.game_states.GameEnded;
import chess_game.game_states.InGame;
import chess_game.game_states.Tie;
import chess_game.moves.AttackMove;
import chess_game.moves.CastleMove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UI {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    private static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE

    private static final String GAME_ENDED_FORMAT = "Game ended in a %s.";
    private static final String WINNER_ANNOUNCEMENT_FORMAT = "The winner is: %s.";
    private static final String GIVE_COMMAND_INPUT = "Give a command (type help for list of commands):";
    private static ChessGame game;
    public static void main(String[] args) {
        game = new ChessGame();
        GameState currentState = game.getGameState();
        System.out.println("Game started!");
        System.out.println("Current board:");
        printBoard(game.getBoardRepresentation());
        while (currentState instanceof InGame) {
            String command;
            System.out.println(GIVE_COMMAND_INPUT);
            Scanner reader = new Scanner(System.in);
            System.out.print(">> ");
            command = reader.nextLine();
            System.out.println("Current board:");
            printBoard(game.getBoardRepresentation());
            handleRequest(command);
        }

        if (currentState instanceof CheckMate) {
            System.out.println(String.format(GAME_ENDED_FORMAT, "Check-Mate"));
            System.out.println(String.format(WINNER_ANNOUNCEMENT_FORMAT, ((CheckMate) currentState).getWinningAlliance()));
        }
        else if (currentState instanceof Tie) {
            System.out.println(String.format(GAME_ENDED_FORMAT, "Tie"));
        }
    }

    public static void handleRequest(String request)
    {
        request = request.toUpperCase().replaceAll(" ", "");
        String[] args = request.split("->");
        if (args.length == 2) {
            if (isProperPosition(args[0]) && isProperPosition(args[1])) {
                handleMoveRequest(args[0], args[1]);
            }
            else {
                System.out.println("Error: position syntax is wrong or out of bound. example for a proper position: A3");
            }
        }
        else if (args.length == 1) {
            if (isProperPosition(args[0])) {
                handleShowPieceMovesRequest(args[0]);
            }
            else if (args[0].equals("HELP")) {
                handleHelpRequest();
            }
            else {
                System.out.println("Error: unknown syntax.");
            }
        }
        else {
            System.out.println("Error: unknown syntax.");
        }
    }
    public static void handleMoveRequest(String pos1, String pos2)
    {
        game.makeMove(new Move(convertStrToPos(pos1), convertStrToPos(pos2)));
    }
    public static void handleShowPieceMovesRequest(String pos)
    {
        Position source = convertStrToPos(pos);
        ArrayList<Move> possibleMoves = (ArrayList<Move>) game.getPossibleMoves();
        String[][] boardRep = game.getBoardRepresentation();
        for (Move move : possibleMoves) {
            if (move.getSource() == source) {
                if (move instanceof AttackMove) {
                    if (boardRep[move.getDestination().getX()][move.getDestination().getY()] != null)
                        boardRep[move.getDestination().getX()][move.getDestination().getY()] += '!';
                    else
                        boardRep[move.getDestination().getX()][move.getDestination().getY()] = "!";
                }
                else if (move instanceof CastleMove) {
                    if (boardRep[move.getDestination().getX()][move.getDestination().getY()] != null)
                        boardRep[move.getDestination().getX()][move.getDestination().getY()] += '@';
                    else
                        boardRep[move.getDestination().getX()][move.getDestination().getY()] = "@";
                }
                else {
                    if (boardRep[move.getDestination().getX()][move.getDestination().getY()] != null)
                        boardRep[move.getDestination().getX()][move.getDestination().getY()] += '.';
                    else
                        boardRep[move.getDestination().getX()][move.getDestination().getY()] = ".";
                }
            }
        }
        printBoard(boardRep);
        //System.out.println(Arrays.toString(game._possibleMovesFinder.getMovesOf(convertStrToPos(pos1)).toArray()));
    }
    private static Position convertStrToPos(String str)
    {
        int x = (int)str.charAt(0) - (int)'A';
        int y = 8 - Integer.parseInt(Character.toString(str.charAt(1)));
        return new Position(x, y);
    }
    public static void handleHelpRequest()
    {
        System.out.println("1) Movement Command\n\tsyntax: [pos1] -> [pos2]\n\texample: A2 -> A4\n2) Show Moves of Specific Piece\n\tsyntax: [pos]\n\texample: G8");
    }
    public static void handleGetGameStateRequest()
    {
        System.out.println(game.getGameState());
    }

    private static boolean isProperPosition(String pos) {
        if (pos.length() == 2) {
            char[] chars = pos.toCharArray();
            return chars[0] >= 'A' && chars[0] <= 'H' && chars[1] >= '0' && chars[1] <= '8';
        }
        return false;
    }

    static void printBoard(String[][] boardRep) {
        String[] tile_backgrounds = {ANSI_WHITE_BACKGROUND, ANSI_RESET};
        String BLANK_TILE = " \u3000 ";
        System.out.print(CYAN_BOLD +ANSI_YELLOW_BACKGROUND +CYAN_BOLD + BLANK_TILE + BLANK_TILE+BLANK_TILE+BLANK_TILE+BLANK_TILE+BLANK_TILE+BLANK_TILE+BLANK_TILE+BLANK_TILE+ "   " + ANSI_RESET);
        for (int y = 0; y < 8; y++) {
            System.out.print("\n" + ANSI_YELLOW_BACKGROUND + BLUE_BOLD + " " + (char)(0xFF10 + (8 - y)) + " " + ANSI_RESET);
            for (int x = 0; x < 8; x++) {
                String piece = boardRep[x][y];
                String background =  tile_backgrounds[(x + y) % 2] +  " " + tile_backgrounds[(x + y) % 2];
                if (piece != null) {
                    switch (piece.toCharArray()[piece.length() - 1]) {
                        case '!':
                            background = RED_BACKGROUND + " " + RED_BACKGROUND;
                            piece = piece.replace("!", "");
                            break;
                        case '@':
                            background = PURPLE_BACKGROUND_BRIGHT + " " + PURPLE_BACKGROUND_BRIGHT;
                            piece = piece.replace("@", "");
                            break;
                        case '.':
                            background = YELLOW_BACKGROUND_BRIGHT + " " + YELLOW_BACKGROUND_BRIGHT;
                            piece = piece.replace(".", "");
                            break;
                    }
                }
                System.out.print(background + (piece == null ? "\033[1;36m\u3000" : piece) + " ");
            }
            System.out.print(ANSI_YELLOW_BACKGROUND + "   " + ANSI_RESET);
        }
        System.out.println("\n" + ANSI_YELLOW_BACKGROUND + BLUE_BOLD + BLANK_TILE +" Ａ  Ｂ  Ｃ  Ｄ  Ｅ  Ｆ  Ｇ  Ｈ    " + ANSI_RESET);
    }
}
