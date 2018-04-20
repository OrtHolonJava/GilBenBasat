package gchess.chess.states;

import gchess.boardgame.states.InGame;
import gchess.boardgame.Position;

public class Check extends InGame {
    private final Position _checkedKing;

    public Check(Position checkedKing) {
        _checkedKing = checkedKing;
    }

    public Position getCheckedKing() {
        return _checkedKing;
    }
}
