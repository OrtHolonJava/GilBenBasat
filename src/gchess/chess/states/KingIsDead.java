package gchess.chess.states;

import gchess.boardgame.Alliance;
import gchess.boardgame.states.GameEnded;

public class KingIsDead extends GameEnded {
    private final Alliance _winningAlliance;

    public KingIsDead(Alliance winningAlliance) {
        _winningAlliance = winningAlliance;
    }

    public Alliance getWinningAlliance() {
        return _winningAlliance;
    }
}
