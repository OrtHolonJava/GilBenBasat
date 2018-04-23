package gchess.chess.gamemods;

import gchess.boardgame.BoardGame;
import gchess.boardgame.Position;
import gchess.chess.ChessBoard;
import gchess.chess.ChessGame;
import gchess.chess.pieces.*;
import javafx.geometry.Pos;

import java.util.*;

import static gchess.boardgame.Alliance.BLACK;
import static gchess.boardgame.Alliance.WHITE;

public class Chess960 extends ChessGame {

    @Override
    public BoardGame<ChessBoard> getCopy() {
        Chess960 game = new Chess960();
        game._currentAlliance = _currentAlliance;
        game._board = (ChessBoard) _board.getCopy();
        return game;
    }

    @Override
    protected void initBoard() {
        ArrayList<Integer> homeRankFreeXIndex  = new ArrayList<>(); // indicates which indexes of home rank are free
        homeRankFreeXIndex.add(0);
        homeRankFreeXIndex.add(1);
        homeRankFreeXIndex.add(2);
        homeRankFreeXIndex.add(3);
        homeRankFreeXIndex.add(4);
        homeRankFreeXIndex.add(5);
        homeRankFreeXIndex.add(6);
        homeRankFreeXIndex.add(7);

        Random rnd = new Random();
        int index;
        Integer x;
        // selects position for the first bishop
        x = rnd.nextInt(4) * 2; // even-tile bishop
        _board.setPiece(new Position(x, 0), new Bishop(BLACK));
        _board.setPiece(new Position(x, 7), new Bishop(WHITE));
        homeRankFreeXIndex.remove(x);

        // selects position for the second bishop
        x = rnd.nextInt(4) * 2 + 1; // odd-tile bishop
        _board.setPiece(new Position(x, 0), new Bishop(BLACK));
        _board.setPiece(new Position(x, 7), new Bishop(WHITE));
        homeRankFreeXIndex.remove(x);

        // select position for queen
        index = rnd.nextInt(6); // there is only 6 indexes left
        x = homeRankFreeXIndex.get(index);
        _board.setPiece(new Position(x, 0), new Queen(BLACK));
        _board.setPiece(new Position(x, 7), new Queen(WHITE));
        homeRankFreeXIndex.remove(x);

        // select position for the first knight
        index = rnd.nextInt(5); // there is only 5 indexes left
        x = homeRankFreeXIndex.get(index);
        _board.setPiece(new Position(x, 0), new Knight(BLACK));
        _board.setPiece(new Position(x, 7), new Knight(WHITE));
        homeRankFreeXIndex.remove(x);

        // select position for the second knight
        index = rnd.nextInt(4); // there is only 4 indexes left
        x = homeRankFreeXIndex.get(index);
        _board.setPiece(new Position(x, 0), new Knight(BLACK));
        _board.setPiece(new Position(x, 7), new Knight(WHITE));
        homeRankFreeXIndex.remove(x);

        // calculate sort the remaining indexes so that the king would be in the middle of the two rooks.
        Collections.sort(homeRankFreeXIndex);

        // set left rooks position
        _board.setPiece(new Position(homeRankFreeXIndex.get(0), 0), new Rook(BLACK));
        _board.setPiece(new Position(homeRankFreeXIndex.get(0), 7), new Rook(WHITE));

        // set right rooks position
        _board.setPiece(new Position(homeRankFreeXIndex.get(2), 0), new Rook(BLACK));
        _board.setPiece(new Position(homeRankFreeXIndex.get(2), 7), new Rook(WHITE));

        // set king position
        _board.setPiece(new Position(homeRankFreeXIndex.get(1), 0), new King(BLACK));
        _board.setPiece(new Position(homeRankFreeXIndex.get(1), 7), new King(WHITE));


        // set pawn positions
        _board.setPiece(new Position(0, 1), new Pawn(BLACK));
        _board.setPiece(new Position(1, 1), new Pawn(BLACK));
        _board.setPiece(new Position(2, 1), new Pawn(BLACK));
        _board.setPiece(new Position(3, 1), new Pawn(BLACK));
        _board.setPiece(new Position(4, 1), new Pawn(BLACK));
        _board.setPiece(new Position(5, 1), new Pawn(BLACK));
        _board.setPiece(new Position(6, 1), new Pawn(BLACK));
        _board.setPiece(new Position(7, 1), new Pawn(BLACK));
        _board.setPiece(new Position(0, 6), new Pawn(WHITE));
        _board.setPiece(new Position(1, 6), new Pawn(WHITE));
        _board.setPiece(new Position(2, 6), new Pawn(WHITE));
        _board.setPiece(new Position(3, 6), new Pawn(WHITE));
        _board.setPiece(new Position(4, 6), new Pawn(WHITE));
        _board.setPiece(new Position(5, 6), new Pawn(WHITE));
        _board.setPiece(new Position(6, 6), new Pawn(WHITE));
        _board.setPiece(new Position(7, 6), new Pawn(WHITE));

    }
}
