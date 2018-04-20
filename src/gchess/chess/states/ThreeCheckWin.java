package gchess.chess.states;

import gchess.boardgame.Alliance;
import gchess.boardgame.states.GameEnded;

public class ThreeCheckWin extends GameEnded {
    private final Alliance _winningAlliance;

    public ThreeCheckWin(Alliance winningAlliance) {
        _winningAlliance = winningAlliance;
    }

    public Alliance getWinningAlliance() {
        return _winningAlliance;
    }
}
