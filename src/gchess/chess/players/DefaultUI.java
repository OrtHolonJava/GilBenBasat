package gchess.chess.players;

import gchess.boardgame.Move;
import gchess.boardgame.Position;
import gchess.chess.ChessGame;
import gchess.chess.enums.Piece;
import gchess.chess.moves.AttackMove;
import gchess.chess.moves.CastleMove;

import java.util.ArrayList;
import java.util.Scanner;

public class DefaultUI implements UI {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    private static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    private static final String RED_BACKGROUND = "\033[41m";    // RED
    private static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    private static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE

    private static final String GIVE_COMMAND_INPUT = "Give a command (type help for list of commands):";
    private ChessGame _game;
    private Move _returnedMove;

    public DefaultUI() {
        _returnedMove = null;
    }

    @Override
    public Move getNextMove(ChessGame game) {
        _game = game;
        System.out.println("Current board:");
        printBoard(_game.getBoardRepresentation());
        while (_returnedMove == null) {
            String command;
            System.out.println(GIVE_COMMAND_INPUT);
            Scanner reader = new Scanner(System.in);
            System.out.print(">> ");
            command = reader.nextLine();
            handleRequest(command);
        }
        return _returnedMove;
    }

    @Override
    public void onGameStarted(ChessGame game) {
        System.out.println("Game started:");
        printBoard(_game.getBoardRepresentation());
    }

    @Override
    public void onPlayerMadeMove(ChessGame game) {

    }

    @Override
    public void onGameEnded(ChessGame game) {
        System.out.println(game.getGameState());
    }

    private void handleRequest(String request)
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
    private void handleMoveRequest(String pos1, String pos2)
    {
        Position source = convertStrToPos(pos1);
        Position destination = convertStrToPos(pos2);
        for (Move move : _game.getPossibleMoves()) {
            if (move.getSource().equals(source) && move.getDestination().equals(destination)) {
                _game.makeMove(move);
                System.out.println("Current board:");
                printBoard(_game.getBoardRepresentation());
                _returnedMove = move;
                return;
            }
        }
        System.out.println("Current board:");
        printBoard(_game.getBoardRepresentation());
        System.out.println("Error: impossible move.");
    }
    private void handleShowPieceMovesRequest(String pos)
    {
        Position source = convertStrToPos(pos);
        ArrayList<Move> possibleMoves = (ArrayList<Move>) _game.getPossibleMoves();
        String[][] boardRep = getBoardRepInStr(_game.getBoardRepresentation());
        for (Move move : possibleMoves) {
            if (move.getSource().equals(source)) {
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
        System.out.println("Current board with available destinations:");
        printBoard(boardRep);
    }
    private static String[][] getBoardRepInStr(Piece[][] boardRep) {
        String[][] newBoardRep = new String[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newBoardRep[i][j] = pieceToStr(boardRep[i][j]);
            }
        }
        return newBoardRep;
    }
    private Position convertStrToPos(String str)
    {
        int x = (int)str.charAt(0) - (int)'A';
        int y = 8 - Integer.parseInt(Character.toString(str.charAt(1)));
        return new Position(x, y);
    }
    private void handleHelpRequest()
    {
        System.out.println("Current board:");
        printBoard(_game.getBoardRepresentation());
        System.out.println("1) Movement Command\n\tsyntax: [pos1] -> [pos2]\n\texample: A2 -> A4\n2) Show Moves of Specific Piece\n\tsyntax: [pos]\n\texample: G8");
    }

    private static boolean isProperPosition(String pos) {
        if (pos.length() == 2) {
            char[] chars = pos.toCharArray();
            return chars[0] >= 'A' && chars[0] <= 'H' && chars[1] >= '0' && chars[1] <= '8';
        }
        return false;
    }

    private static String pieceToStr(Piece piece) {
        switch (piece) {
            case WHITE_KING:
                return "\033[1;36m\u2654";
            case WHITE_PAWN:
                return "\033[1;36m\u2659";
            case WHITE_ROOK:
                return "\033[1;36m\u2656";
            case WHITE_QUEEN:
                return "\033[1;36m\u2655";
            case WHITE_BISHOP:
                return "\033[1;36m\u2657";
            case WHITE_KNIGHT:
                return "\033[1;36m\u2658";
            case BLACK_KING:
                return "\033[1;30m\u2654";
            case BLACK_PAWN:
                return "\033[1;30m\u2659";
            case BLACK_ROOK:
                return "\033[1;30m\u2656";
            case BLACK_QUEEN:
                return "\033[1;30m\u2655";
            case BLACK_BISHOP:
                return "\033[1;30m\u2657";
            case BLACK_KNIGHT:
                return "\033[1;30m\u2658";
        }
        return null;
    }

    private static void printBoard(Piece[][] boardEnumRep) {
        String[][] boardRep = getBoardRepInStr(boardEnumRep);
        printBoard(boardRep);
    }

    private static void printBoard(String[][] boardRep) {
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
                if (piece == null || piece.equals("")) {
                    piece = "\033[1;36m\u3000";
                }
                System.out.print(background + piece + " ");
            }
            System.out.print(ANSI_YELLOW_BACKGROUND + "   " + ANSI_RESET);
        }
        System.out.println("\n" + ANSI_YELLOW_BACKGROUND + BLUE_BOLD + BLANK_TILE +" Ａ  Ｂ  Ｃ  Ｄ  Ｅ  Ｆ  Ｇ  Ｈ    " + ANSI_RESET);
    }
}
