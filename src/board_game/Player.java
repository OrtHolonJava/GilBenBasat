package board_game;

public abstract class Player<T extends BoardGame> {
    private String _name;
    private Alliance _alliance;
    public Player(String name, Alliance alliance) {
        _name = name;
        _alliance = alliance;
    }
    public abstract Move getNextMove(T game);

    public abstract void onGameEnded(T game);
}
