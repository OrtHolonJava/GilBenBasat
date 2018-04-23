package gui;

import gchess.GChess;
import gchess.boardgame.*;
import gchess.boardgame.states.GameEnded;
import gchess.chess.ChessGame;
import gchess.chess.enums.Piece;
import gchess.chess.moves.AttackMove;
import gchess.chess.moves.CastleMove;
import gchess.chess.players.UI;
import gchess.chess.states.*;
import gchess.enums.GameMode;
import gchess.exceptions.GChessThrowable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.prefs.Preferences;

import static gchess.chess.utils.ChessGameUtils.enemyOf;

/**
 * Created by Gil on 30/10/2017.
 */
public class GamePanel extends JPanel implements UI {
    public final Object lock = new Object();
    private ChessGame _currentGame = null;
    private BoardPanel _board;
    private boolean flipBoard;
    private boolean flipBoardFromStart;
    private int _turn;
    private Move _nextMove = null;
    private Position _selectedPosition = null;
    private ArrayList<Position> _possibleDestinations = null;
    public GamePanel() {
        _turn = 0;
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(300, 10));
        add(panel, BorderLayout.EAST);
        panel.setLayout(new BorderLayout(0, 0));

        JPanel panel_3 = new JPanel();
        panel.add(panel_3, BorderLayout.NORTH);
        panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.PAGE_AXIS));

        JLabel label = new JLabel("Black");
        panel_3.add(label);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setFont(new Font("Tahoma", Font.PLAIN, 26));

        /*JLabel label_1 = new JLabel("10:00 +3s");
        panel_3.add(label_1);
        label_1.setVerticalAlignment(SwingConstants.BOTTOM);
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 18));*/

        JPanel panel_4 = new JPanel();
        panel.add(panel_4, BorderLayout.SOUTH);
        panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.PAGE_AXIS));

        JLabel lblBlack = new JLabel("White");
        panel_4.add(lblBlack);
        lblBlack.setFont(new Font("Tahoma", Font.PLAIN, 26));

        /*JLabel lbls = new JLabel("10:00 +3s");
        panel_4.add(lbls);
        lbls.setHorizontalAlignment(SwingConstants.CENTER);
        lbls.setFont(new Font("Tahoma", Font.PLAIN, 18));*/

        JPanel panel_1 = new JPanel();
        add(panel_1, BorderLayout.SOUTH);

        /*JButton btnSurrender = new JButton("Surrender");
        btnSurrender.setHorizontalAlignment(SwingConstants.LEFT);
        btnSurrender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _nextMove = new SurrenderMove();
                synchronized (lock) {
                    lock.notify();
                }
            }
        });
        panel_1.add(btnSurrender);

        /*JButton btnOfferADraw = new JButton("Offer a Draw");
        panel_1.add(btnOfferADraw);*/
        _board = new BoardPanel();
        JPanel panel1 = new JPanel();
        panel1.add(_board);
        add(panel1);
    }

    private GameMode strToGameMode(String mode) {
        switch (mode) {
            case "CLASSIC":
                return GameMode.CLASSIC;
            case "ATOMIC":
                return GameMode.ATOMIC;
            case "CHESS960":
                return GameMode.CHESS960;
            case "KING_OF_THE_HILL":
                return GameMode.KING_OF_THE_HILL;
            case "THREE_CHECK":
                return GameMode.THREE_CHECK;
        }
        return null;
    }

    private ArrayList<Tile> toTileList(Piece[][] pieces) {
        ArrayList<Tile> tiles = new ArrayList<>();
        Color[] backgroungColors = {Color.WHITE, Color.BLACK};
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tiles.add(new Tile(i * 8 + j, backgroungColors[(j + i) % 2], pieces[j][i], this));
            }
        }
        return tiles;
    }

    @Override
    public Move getNextMove(ChessGame game) {
        _currentGame = game;
        while (true) {
            synchronized (lock) {
                try {
                    lock.wait();
                    return _nextMove;
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onGameStarted(ChessGame game) {
        _currentGame = game;
        drawBoard(toTileList(game.getBoardRepresentation()));
    }

    @Override
    public void onPlayerMadeMove(ChessGame game) {
        _currentGame = game;
        drawBoard(toTileList(game.getBoardRepresentation()));
    }

    @Override
    public void onGameEnded(ChessGame game) {
        _currentGame = game;
        _board.setTiles(toTileList(game.getBoardRepresentation()));
        GameEnded state = (GameEnded)game.getGameState();
        showEndGameMessage(state);
    }

    private void showEndGameMessage(GameEnded state) {
        String message;
        if (state instanceof CheckMate) {
            message = ((CheckMate) state).getWinningAlliance().toString() + " won with Checkmate!";
        } else if (state instanceof KingIsDead) {
            message = ((KingIsDead) state).getWinningAlliance().toString() + " won after bombing his opponent king!";
        } else if (state instanceof KingIsOnTheHill) {
            message = ((KingIsOnTheHill) state).getWinningAlliance().toString() + " won after getting the king up the hill!";
        } else if (state instanceof ThreeCheckWin) {
            message = ((ThreeCheckWin) state).getWinningAlliance().toString() + " won after causing opponents king a heart attack!";
        } else {
            message = "Game ended with a Tie!";
        }
        Object[] options = {"OK"};
        int n = JOptionPane.showOptionDialog(this,
            message,"Game ended",
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        _currentGame = null;
        _selectedPosition = null;
        _nextMove = null;
        _possibleDestinations = null;
        JPanel cards = ((MainFrame) SwingUtilities.getWindowAncestor(this)).cards;
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, "0");
    }

    public void onTileClicked(int id) {
        Position pos = new Position(id % 8, id / 8);
        if (_selectedPosition != null) {
            if (_possibleDestinations.contains(pos)) {
                makeMove(_selectedPosition, pos);
                _selectedPosition = null;
                return;
            }
        }
        _selectedPosition = pos;
        setDestinationsForPosition(_selectedPosition);
    }

    private void makeMove(Position source, Position destination) {
        _turn++;
        for (Move move : _currentGame.getPossibleMoves()) {
            if (move.getSource().equals(source) && move.getDestination().equals(destination)) {
                _nextMove = move;
                synchronized (lock) {
                    lock.notify();
                }
                return;
            }
        }
    }

    private void setDestinationsForPosition(Position selectedPosition) {
        _possibleDestinations = new ArrayList<>();
        ArrayList<Tile> currentTiles = toTileList(_currentGame.getBoardRepresentation());
        Color backgroundColor;
        for (Move move : _currentGame.getPossibleMoves()) {
            if (move.getSource().equals(selectedPosition)) {
                _possibleDestinations.add(move.getDestination());
                if (move instanceof AttackMove) {
                    backgroundColor = Color.RED;
                } else if (move instanceof CastleMove) {
                    backgroundColor = Color.MAGENTA;
                } else {
                    backgroundColor = Color.YELLOW;
                }
                currentTiles.get(positionToTileIndex(move.getDestination())).setBackgroundColor(backgroundColor);
            }
        }
        drawBoard(currentTiles);
    }

    private void drawBoard(ArrayList<Tile> tiles) {
        if (flipBoard && _turn % 2 == 1) {
            Collections.reverse(tiles);
        }
        if (flipBoardFromStart) {
            Collections.reverse(tiles);
        }
        _board.setTiles(tiles);
        _board.revalidate();
        _board.repaint();
    }

    private int positionToTileIndex(Position pos) {
        return pos.getY() * 8 + pos.getX();
    }

    public void start()
    {
        GChess gChess = new GChess();
        Preferences prefs = Preferences.userNodeForPackage(this.getClass());
        if (prefs.getBoolean("isAiGame", true)) {
            try {
                gChess.startNewPlayerVsAiGame(strToGameMode(prefs.get("gameMode", "CLASSIC")),
                    prefs.getBoolean("isWhite", true) ? Alliance.WHITE: Alliance.BLACK,
                    prefs.getInt("aiDifficulty", 4), this);
                flipBoardFromStart = !prefs.getBoolean("isWhite", true);
            } catch (GChessThrowable gChessThrowable) {
                gChessThrowable.printStackTrace();
            }
        }
        else {
            try {
                gChess.startNewPlayerVsPlayerGame(strToGameMode(prefs.get("gameMode", "CLASSIC")), this);
                flipBoard = prefs.getBoolean("toFlip", true);
            } catch (GChessThrowable gChessThrowable) {
                gChessThrowable.printStackTrace();
            }
        }
    }
}
