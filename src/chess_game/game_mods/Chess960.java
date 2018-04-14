package chess_game.game_mods;

import board_game.Position;
import chess_game.ChessGame;
import chess_game.pieces.*;

import java.util.Random;

import static board_game.Alliance.*;

public class Chess960 extends ChessGame {
    @Override
    protected void initBoard() {
        int[] homeRankFreePos = {0, 1, 2, 3, 4, 5, 6, 7}; // indicates which indexes of home rank are free. at start all are free.
        Random rnd = new Random();
        int index;
        // selects position for the first bishop
        index = (rnd.nextInt() % 4) * 2; // even-tile bishop
        _board.setPiece(new Position(index, 0), new Bishop(BLACK));
        _board.setPiece(new Position(index, 7), new Bishop(WHITE));
        homeRankFreePos[index] = homeRankFreePos[7];
        // selects position for the second bishop
        index = (rnd.nextInt() % 4) * 2 + 1;
        _board.setPiece(new Position(index, 0), new Bishop(BLACK));
        _board.setPiece(new Position(index, 7), new Bishop(WHITE));
        homeRankFreePos[index] = homeRankFreePos[6];

        // select position for queen
        index = rnd.nextInt() % 6;
        _board.setPiece(new Position(homeRankFreePos[index], 0), new Queen(BLACK));
        _board.setPiece(new Position(homeRankFreePos[index], 7), new Queen(WHITE));
        homeRankFreePos[index] = homeRankFreePos[5];

        // select position for the first knight
        index = rnd.nextInt() % 5;
        _board.setPiece(new Position(homeRankFreePos[index], 0), new Knight(BLACK));
        _board.setPiece(new Position(homeRankFreePos[index], 7), new Knight(WHITE));
        homeRankFreePos[index] = homeRankFreePos[4];

        // select position for the second knight
        index = rnd.nextInt() % 4;
        _board.setPiece(new Position(homeRankFreePos[index], 0), new Knight(BLACK));
        _board.setPiece(new Position(homeRankFreePos[index], 7), new Knight(WHITE));
        homeRankFreePos[index] = homeRankFreePos[3];

        // set rooks and king position
        _board.setPiece(new Position(homeRankFreePos[0], 0), new Rook(BLACK));
        _board.setPiece(new Position(homeRankFreePos[0], 7), new Rook(WHITE));

        // set rooks and king position
        _board.setPiece(new Position(homeRankFreePos[1], 0), new King(BLACK));
        _board.setPiece(new Position(homeRankFreePos[1], 7), new King(WHITE));

        // set rooks and king position
        _board.setPiece(new Position(homeRankFreePos[2], 0), new Rook(BLACK));
        _board.setPiece(new Position(homeRankFreePos[2], 7), new Rook(WHITE));

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
