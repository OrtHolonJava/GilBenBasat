package chess_game.game_mods;

import board_game.Alliance;
import board_game.GameState;
import board_game.Move;
import board_game.game_states.GameEnded;
import chess_game.ChessGame;
import chess_game.game_states.ThreeCheckWin;

import java.util.HashMap;

import static chess_game.utils.ChessGameUtils.enemyOf;

public class ThreeCheck extends ChessGame {
    private HashMap<Alliance, Integer> _allianceCheckCount;
    public ThreeCheck() {
        super();
        _allianceCheckCount = new HashMap<>();
    }

    @Override
    protected void onMakeMoveFinished(Move move) {
        if (isCheck()) {
            int newCheckCount = _allianceCheckCount.get(getCurrentTurnAlliance()) + 1;
            _allianceCheckCount.put(getCurrentTurnAlliance(), newCheckCount);
        }
    }

    @Override
    protected void onUndoMoveStarted(Move move) {
        if (isCheck()) {
            int newCheckCount = _allianceCheckCount.get(getCurrentTurnAlliance()) - 1;
            _allianceCheckCount.put(getCurrentTurnAlliance(), newCheckCount);
        }
    }

    @Override
    protected GameEnded getSpecialEndGameState() {
        if (_allianceCheckCount.get(getCurrentTurnAlliance()) >= 3) {
            return new ThreeCheckWin(enemyOf(getCurrentTurnAlliance()));
        }
        return null;
    }

    public int getCheckCountOf(Alliance alliance) {
        return _allianceCheckCount.get(alliance);
    }
}
