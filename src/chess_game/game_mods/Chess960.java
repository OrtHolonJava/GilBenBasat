package chess_game.game_mods;

import board_game.BoardGame;
import board_game.Position;
import chess_game.ChessBoard;
import chess_game.ChessGame;
import chess_game.pieces.*;

import java.util.Arrays;
import java.util.Random;

import static board_game.Alliance.*;

public class Chess960 extends ChessGame {

    @Override
    public BoardGame<ChessBoard> getCopy() {
        Chess960 game = new Chess960();
        game._allianceCycle = _allianceCycle.clone();
        game._allianceTurnIndex = _allianceTurnIndex;
        game._board = (ChessBoard) _board.getCopy();
        return game;
    }

    @Override
    protected void initBoard() {
        int[] homeRankFreePos = {0, 1, 2, 3, 4, 5, 6, 7}; // indicates which indexes of home rank are free. at start all are free.
        Random rnd = new Random();
        int index;
        // selects position for the first bishop
        index = rnd.nextInt(4) * 2; // even-tile bishop
        _board.setPiece(new Position(index, 0), new Bishop(BLACK));
        _board.setPiece(new Position(index, 7), new Bishop(WHITE));
        homeRankFreePos[index] = homeRankFreePos[7];
        // selects position for the second bishop
        index = rnd.nextInt(4) * 2 + 1;
        _board.setPiece(new Position(index, 0), new Bishop(BLACK));
        _board.setPiece(new Position(index, 7), new Bishop(WHITE));
        homeRankFreePos[index] = homeRankFreePos[6];

        // select position for queen
        index = rnd.nextInt(6);
        _board.setPiece(new Position(homeRankFreePos[index], 0), new Queen(BLACK));
        _board.setPiece(new Position(homeRankFreePos[index], 7), new Queen(WHITE));
        homeRankFreePos[index] = homeRankFreePos[5];

        // select position for the first knight
        index = rnd.nextInt(5);
        _board.setPiece(new Position(homeRankFreePos[index], 0), new Knight(BLACK));
        _board.setPiece(new Position(homeRankFreePos[index], 7), new Knight(WHITE));
        homeRankFreePos[index] = homeRankFreePos[4];

        // select position for the second knight
        index = rnd.nextInt(4);
        _board.setPiece(new Position(homeRankFreePos[index], 0), new Knight(BLACK));
        _board.setPiece(new Position(homeRankFreePos[index], 7), new Knight(WHITE));
        homeRankFreePos[index] = homeRankFreePos[3];

        // calculate min, mid and max so that the king would be in the middle of the two rooks.
        int[] indexes = new int[3];
        indexes[0] = homeRankFreePos[0];
        indexes[1] = homeRankFreePos[1];
        indexes[2] = homeRankFreePos[2];
        Arrays.sort(indexes);

        // set rooks position position
        _board.setPiece(new Position(indexes[0], 0), new Rook(BLACK));
        _board.setPiece(new Position(indexes[0], 7), new Rook(WHITE));

        _board.setPiece(new Position(indexes[2], 0), new Rook(BLACK));
        _board.setPiece(new Position(indexes[2], 7), new Rook(WHITE));

        // set king position
        _board.setPiece(new Position(indexes[1], 0), new King(BLACK));
        _board.setPiece(new Position(indexes[1], 7), new King(WHITE));


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
