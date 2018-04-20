package gchess.chess.states;

import gchess.boardgame.Alliance;
import gchess.boardgame.states.GameEnded;
import gchess.boardgame.Position;

public class CheckMate extends GameEnded {
    private final Position _checkedKingPos;
    private final Alliance _winningAlliance;

    public CheckMate(Position checkedKingPos, Alliance winningAlliance) {
        _checkedKingPos = checkedKingPos;
        _winningAlliance = winningAlliance;
    }

    public Position getCheckedKingPos() {
        return _checkedKingPos;
    }

    public Alliance getWinningAlliance() {
        return _winningAlliance;
    }
}
