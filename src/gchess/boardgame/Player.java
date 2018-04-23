package gchess.boardgame;

/**
 * <h1>Player</h1>
 * A player is the entity that communicates with the BoardGamePlatform.
 * What it does is to give a move every time the game platform ask from it whether it is a human or a computer making
 * the move.
 * @param <T> The game that the player is compatible playing.
 *
 * @see BoardGamePlatform
 *
 * @author  Gil Ben Basat
 * @version 1.0
 * @since   2018-04-23
 */
public abstract class Player<T extends BoardGame> {
    protected String _name;
    protected Alliance _alliance;
    public Player(String name, Alliance alliance) {
        _name = name;
        _alliance = alliance;
    }

    /**
     * This function is called by the board-game platform when after of the players of the game has made a move.
     * @param game The current game (after the move).
     */
    public abstract void onPlayerMadeMove(T game);

    /**
     * This function is called by the board-game platform when the game initially started.
     * @param game The current game.
     */
    public abstract void onGameStarted(T game);

    /**
     * This function is called by the board-game platform as a request for a move from the player.
     * @param game The current game (feel free to get any data from the game and make any changes you want as it is
     *             only a copy of the game).
     */
    public abstract Move getNextMove(T game);


    /**
     * This function is called by the board-game platform after the game has ended.
     * @param game The current game.
     */
    public abstract void onGameEnded(T game);

    public Alliance getAlliance() {
        return _alliance;
    }

    public String getName() {
        return _name;
    }
}
