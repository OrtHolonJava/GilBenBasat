package gchess.chess.gamemods;

import gchess.boardgame.Alliance;
import gchess.boardgame.BoardGame;
import gchess.boardgame.Move;
import gchess.boardgame.states.GameEnded;
import gchess.chess.ChessBoard;
import gchess.chess.ChessGame;
import gchess.chess.states.ThreeCheckWin;

import java.util.HashMap;

import static gchess.chess.utils.ChessGameUtils.enemyOf;

public class ThreeCheck extends ChessGame {
    private HashMap<Alliance, Integer> _allianceCheckCount;
    public ThreeCheck() {
        super();
        _allianceCheckCount = new HashMap<>();
        _allianceCheckCount.put(Alliance.WHITE, 0);
        _allianceCheckCount.put(Alliance.BLACK, 0);
    }

    @Override
    public BoardGame<ChessBoard> getCopy() {
        ThreeCheck game = new ThreeCheck();
        game._currentAllianceTurn = _currentAllianceTurn;
        game._board = (ChessBoard) _board.getCopy();
        game._allianceCheckCount = (HashMap<Alliance, Integer>) _allianceCheckCount.clone();
        return game;
    }

    @Override
    protected void onMakeMoveFinished(Move move) {
        if (isUnderCheck()) {
            int newCheckCount = _allianceCheckCount.get(getCurrentTurnAlliance()) + 1;
            _allianceCheckCount.put(getCurrentTurnAlliance(), newCheckCount);
        }
    }

    @Override
    protected void onUndoMoveStarted(Move move) {
        if (isUnderCheck()) {
            int newCheckCount = _allianceCheckCount.get(getCurrentTurnAlliance()) - 1;
            _allianceCheckCount.put(getCurrentTurnAlliance(), newCheckCount);
        }
    }

    @Override
    protected GameEnded getSpecialEndGameState() {
        if (3 <= _allianceCheckCount.get(getCurrentTurnAlliance())) {
            return new ThreeCheckWin(enemyOf(getCurrentTurnAlliance()));
        }
        return null;
    }

    public int getCheckCountOf(Alliance alliance) {
        return _allianceCheckCount.get(alliance);
    }
}
