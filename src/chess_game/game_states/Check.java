package chess_game.game_states;

import board_game.GameState;
import board_game.Position;

public class Check extends InGame {
    private final Position _checkedKing;

    public Check(Position checkedKing) {
        _checkedKing = checkedKing;
    }

    public Position getCheckedKing() {
        return _checkedKing;
    }
}
