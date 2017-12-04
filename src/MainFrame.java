import javax.swing.*;

public class MainFrame extends JFrame {
    private MainPanel _mainPanel;
    public MainFrame() {
        _mainPanel = new MainPanel();
        add(_mainPanel);
        setSize(1000,1000);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String args[]) {
        MainFrame mainFrame = new MainFrame();
    }
}
