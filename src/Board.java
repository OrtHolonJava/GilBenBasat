import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Board extends JPanel {
    final static int TILE_LENGTH = 115;
    Piece[][] _board = new Piece[8][8];
    int _selectedTile = -2;
    public Board () {
        BoardInitializer.initialize(_board);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                super.mousePressed(me);
                System.out.println("hi");
                if (_selectedTile == me.getY() / TILE_LENGTH * 8 + me.getX() / TILE_LENGTH)
                    _selectedTile = -1;
                else
                    _selectedTile = me.getY() / TILE_LENGTH * 8 + me.getX() / TILE_LENGTH;
                System.out.println(_selectedTile);
                repaint();
            }
        });
         setSize(TILE_LENGTH * 8, TILE_LENGTH * 8);
    }

    public void setBoard(Piece[][] board) {
        _board = board;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color[] colors = {Color.darkGray, Color.WHITE};
        // draw the balck and white grid
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8;  j++) {
                g.setColor(colors[(i + j) % 2]);
                g.fillRect(j * TILE_LENGTH, i * TILE_LENGTH, TILE_LENGTH, TILE_LENGTH);
            }
        }
        /*// draw selected tile
        if (_selectedTile != -1) {
            g.setColor(Color.yellow);
            g.fillRect(_selectedTile % 8 * TILE_LENGTH, _selectedTile / 8 * TILE_LENGTH, TILE_LENGTH, TILE_LENGTH);
        }*/
        // draw the pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8;  j++) {
                if (_board[i][j] == Piece.Empty)
                    continue;
                g.setColor(_board[i][j].getColor());
                Image img = null;
                File f = null;
                try {
                    f = new File("src/Pics/" + _board[i][j] + ".png");
                    img = ImageIO.read(f);
                    img = img.getScaledInstance(TILE_LENGTH, TILE_LENGTH, Image.SCALE_DEFAULT);
                } catch (IOException e) {
                    System.out.println(f.getAbsolutePath());
                }
                g.drawImage(img, i * TILE_LENGTH, j * TILE_LENGTH, null);
            }
        }
    }
}
