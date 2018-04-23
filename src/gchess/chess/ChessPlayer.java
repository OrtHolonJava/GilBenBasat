package gchess.chess;

import gchess.boardgame.Alliance;
import gchess.boardgame.BoardGamePlatform;
import gchess.boardgame.Player;

/**
 * <h1>Chess Player</h1>
 * A chess player is the entity that communicates with the ChessGamePlatform.
 * What it does is to give a move every time the game platform ask from it.
 * A chess player is either a human or a computer.
 *
 * @see ChessGamePlatform
 *
 * @author  Gil Ben Basat
 * @version 1.0
 * @since   2018-04-23
 */
public abstract class ChessPlayer extends Player<ChessGame> {
    public ChessPlayer(String name, Alliance alliance) {
        super(name, alliance);
    }
}
