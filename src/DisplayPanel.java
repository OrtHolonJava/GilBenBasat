/*import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

public class DisplayPanel extends JPanel{
    GridPanel _gridPanel;
    public DisplayPanel(int width, int height)
    {
        super();
        _gridPanel = new GridPanel(width, height);
        setLayout(new GridBagLayout());
        add(_gridPanel);
    }
    public void setZoomRate(int zoomRate) {
        this._gridPanel.getGrid().setZoomRate(zoomRate);
    }

    public void setSelectedTileOption(TileType selectedTileOption) {
        _gridPanel.setSelectedTileOption(selectedTileOption);
    }
    public void setAllTilesWithId(String tileId, TileType newTile) {
        _gridPanel.getGrid().setAllTilesWithId(tileId, newTile);
        repaint();
    }
}

class GridPanel extends JPanel{
    TileType _selectedTileOption;
    private Grid _grid;
    public GridPanel(int width, int height)
    {
        super();

        _grid = new Grid(width, height, this, 1);
        _selectedTileOption = new TileType("Empty", "Empty_Id", 0xffffff);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                super.mousePressed(me);
                _grid.set(_selectedTileOption, me.getX() / _grid.getZoomRate() / _grid.getTileLength(), me.getY() / _grid.getZoomRate() / _grid.getTileLength());
                repaint();
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (e.isShiftDown()) {
                    _grid.set(_selectedTileOption, e.getX() / _grid.getZoomRate() / _grid.getTileLength(), e.getY() / _grid.getZoomRate() / _grid.getTileLength());
                    repaint();
                }
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        _grid.draw(g);
        revalidate();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(_grid.getTileLength() * _grid.getWidth() * _grid.getZoomRate() + 1, _grid.getTileLength() * _grid.getHeight() * _grid.getZoomRate() + 1);
    }

    public Grid getGrid() {
        return _grid;
    }
    private ArrayList<Tile> twoDArrayToList(Tile[][] twoDArray) {
        ArrayList<Tile> list = new ArrayList<Tile>();
        for (Tile[] array : twoDArray) {
            list.addAll(Arrays.asList(array));
        }
        return list;
    }
    public void setSelectedTileOption(TileType selectedTileOption) {
        _selectedTileOption = selectedTileOption;
    }
}
*/