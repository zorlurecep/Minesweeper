package View;

import javax.swing.*;
import java.awt.*;


public class GUI extends JPanel {
    public JFrame frame = new JFrame("Minesweeper BETA");
    public int nrOfRows = 8;
    public int nrOfCols = 8;

    public GUI() {
        frame.setSize(1000, 1000);
        frame.setLayout(new GridLayout(nrOfRows, nrOfCols));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}