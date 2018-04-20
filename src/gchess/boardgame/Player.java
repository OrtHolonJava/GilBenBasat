package gchess.boardgame;

public abstract class Player<T extends BoardGame> {
    protected String _name;
    protected Alliance _alliance;
    public Player(String name, Alliance alliance) {
        _name = name;
        _alliance = alliance;
    }

    public abstract void onPlayerMadeMove(T game);

    public abstract void onGameStarted(T game);

    public abstract Move getNextMove(T game);

    public abstract void onGameEnded(T game);

    public Alliance getAlliance() {
        return _alliance;
    }

    public String getName() {
        return _name;
    }
}
