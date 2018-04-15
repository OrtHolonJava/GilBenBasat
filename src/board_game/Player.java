package board_game;

public abstract class Player<T extends BoardGame> {
    private String _name;
    public Player(String name) {
        _name = name;
    }
    public abstract Move getNextMove(T game);

    public abstract void onGameEnded(T game);
}
