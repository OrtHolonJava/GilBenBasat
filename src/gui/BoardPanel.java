package gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BoardPanel extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image img;
        try {
            img = ImageIO.read(new File("src/gui/drawables/ChessBoard.png"));
            Image scaledImage = img.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH);
            g.drawImage(scaledImage, 0, 0, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

