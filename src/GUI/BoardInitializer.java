package GUI;

/**
 * Created by Gil on 07/10/2017.
 */
public class BoardInitializer {
    public BoardInitializer()
    {
    }
    public static void initialize(Piece[][] board)
    {
        Piece[][] _defaultMat = {
                {Piece.BlackRook, Piece.BlackPawn, Piece.Empty, Piece.Empty, Piece.Empty, Piece.Empty, Piece.WhitePawn, Piece.WhiteRook},
                {Piece.BlackKnight, Piece.BlackPawn, Piece.Empty, Piece.Empty, Piece.Empty, Piece.Empty, Piece.WhitePawn, Piece.WhiteKnight},
                {Piece.BlackBishop, Piece.BlackPawn, Piece.Empty, Piece.Empty, Piece.Empty, Piece.Empty, Piece.WhitePawn, Piece.WhiteBishop},
                {Piece.BlackQueen, Piece.BlackPawn, Piece.Empty, Piece.Empty, Piece.Empty, Piece.Empty, Piece.WhitePawn, Piece.WhiteQueen},
                {Piece.BlackKing, Piece.BlackPawn, Piece.Empty, Piece.Empty, Piece.Empty, Piece.Empty, Piece.WhitePawn, Piece.WhiteKing},
                {Piece.BlackBishop, Piece.BlackPawn, Piece.Empty, Piece.Empty, Piece.Empty, Piece.Empty, Piece.WhitePawn, Piece.WhiteBishop},
                {Piece.BlackKnight, Piece.BlackPawn, Piece.Empty, Piece.Empty, Piece.Empty, Piece.Empty, Piece.WhitePawn, Piece.WhiteKnight},
                {Piece.BlackRook, Piece.BlackPawn, Piece.Empty, Piece.Empty, Piece.Empty, Piece.Empty, Piece.WhitePawn, Piece.WhiteRook}};
        /*
        {"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR"},
        {"BP", "BP", "BP", "BP", "BP", "BP", "BP", "BP"},
        {null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null},
        {"WP", "WP", "WP", "WP", "WP", "WP", "WP", "WP"},
        {"WR", "WN", "WB", "WQ", "WK", "WB", "WN", "WR"}};*/
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                    board[i][j] = _defaultMat[i][j];
    }
}
