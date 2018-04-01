package chess_game.game_states;

public class GameEnded {
    private final String _type, _message;
    public GameEnded(String typeOfEnd, String message) {
        _type = typeOfEnd;
        _message = message;
    }

    public String getType() {
        return _type;
    }

    public String getMessage() {
        return _message;
    }
}
